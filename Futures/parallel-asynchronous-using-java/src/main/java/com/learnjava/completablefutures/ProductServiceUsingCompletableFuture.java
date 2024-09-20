package com.learnjava.completablefutures;

import com.learnjava.domain.*;
import com.learnjava.service.InventoryService;
import com.learnjava.service.ProductInfoService;
import com.learnjava.service.ReviewService;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static com.learnjava.util.CommonUtil.stopWatch;
import static com.learnjava.util.LoggerUtil.log;

public class ProductServiceUsingCompletableFuture {
    private final ProductInfoService productInfoService;
    private final ReviewService reviewService;
    private final InventoryService inventoryService;

    public ProductServiceUsingCompletableFuture(ProductInfoService productInfoService, ReviewService reviewService, InventoryService inventoryService) {
        this.productInfoService = productInfoService;
        this.reviewService = reviewService;
        this.inventoryService = inventoryService;
    }

    public Product retrieveProductDetailsClient(String productId) {
        stopWatch.start();

        CompletableFuture<ProductInfo> cfProductInfo = CompletableFuture.supplyAsync(() -> productInfoService.retrieveProductInfo(productId));
        CompletableFuture<Review> cfReview = CompletableFuture.supplyAsync(() -> reviewService.retrieveReviews(productId));

        Product product = cfProductInfo.thenCombine(cfReview, (productInfo, review) -> new Product(productId, productInfo, review))
                .join(); // clock to get the final Product

        stopWatch.stop();
        stopWatch.reset();
        log("Total Time Taken : " + stopWatch.getTime());

        return product;
    }

    public CompletableFuture<Product> retrieveProductDetailsServer(String productId) {
        CompletableFuture<ProductInfo> cfProductInfo = CompletableFuture.supplyAsync(() -> productInfoService.retrieveProductInfo(productId));
        CompletableFuture<Review> cfReview = CompletableFuture.supplyAsync(() -> reviewService.retrieveReviews(productId));
        return cfProductInfo.thenCombine(cfReview, (productInfo, review) -> new Product(productId, productInfo, review));
    }

    public Product retrieveProductDetailsClientWithInventory(String productId) {
        CompletableFuture<ProductInfo> cfProductInfo = CompletableFuture.supplyAsync(() -> productInfoService.retrieveProductInfo(productId))
                .thenApply(productInfo -> { // transformation to populate inventory to Product Info.
                    productInfo.setProductOptions(updateInventory(productInfo));
                    return productInfo;
                });

        CompletableFuture<Review> cfReview = CompletableFuture.supplyAsync(() -> reviewService.retrieveReviews(productId));
        return cfProductInfo.thenCombine(cfReview, (productInfo, review) -> new Product(productId, productInfo, review))
                .join();
    }

    private List<ProductOption> updateInventory(ProductInfo productInfo) {
        return productInfo.getProductOptions().stream()
                .peek(productOption -> {
                    Inventory inventory = inventoryService.retrieveInventory(productOption); // adds additional 0.5 secs to execution time
                    productOption.setInventory(inventory);
                }).collect(Collectors.toList());
    }

    public Product retrieveProductDetailsClientWithInventoryCF(String productId) {
        CompletableFuture<ProductInfo> cfProductInfo = CompletableFuture.supplyAsync(() -> productInfoService.retrieveProductInfo(productId))
                .thenApply(productInfo -> { // transformation to populate inventory to Product Info.
                    productInfo.setProductOptions(updateInventoryCF(productInfo));
                    return productInfo;
                });

        CompletableFuture<Review> cfReview = CompletableFuture.supplyAsync(() -> reviewService.retrieveReviews(productId))
                .exceptionally(exception -> { // exception will be handled and a dummy review will be returned
                    log("Handled the exception in reviewService: " + exception.getMessage());
                    return Review.builder()
                            .noOfReviews(0)
                            .overallRating(0.0)
                            .build();
                });

        return cfProductInfo.thenCombine(cfReview, (productInfo, review) -> new Product(productId, productInfo, review))
                .whenComplete((product, exception) -> {
                    if (exception != null)
                        log("Inside whenComplete: " + product + " and exception raised is: " + exception.getMessage());
                })
                // there's no dummy product that can be created if the productInfoService fails to retrieve the product, simply log the exception
                .join();
    }

    private List<ProductOption> updateInventoryCF(ProductInfo productInfo) {
        List<CompletableFuture<ProductOption>> productOptionList = productInfo.getProductOptions()
                .stream()
                .map(productOption -> inventoryService.retrieveInventoryCF(productOption)
                        .exceptionally(exception -> {
                            log("Exception Raised while retrieving Inventory: " + exception.getMessage());
                            return Inventory.builder().count(1).build();
                        })
                        .thenApply(inventory -> {
                            productOption.setInventory(inventory);
                            return productOption;
                        }))
                .collect(Collectors.toList());
        return productOptionList.stream().map(CompletableFuture::join).collect(Collectors.toList());
    }

    public Product retrieveProductDetailsClientWithInventoryCFInstructor(String productId) {
        CompletableFuture<ProductInfo> cfProductInfo = CompletableFuture.supplyAsync(() -> productInfoService.retrieveProductInfo(productId))
                .thenApply(productInfo -> { // transformation to populate inventory to Product Info.
                    productInfo.setProductOptions(updateInventoryCFInstructor(productInfo));
                    return productInfo;
                });

        CompletableFuture<Review> cfReview = CompletableFuture.supplyAsync(() -> reviewService.retrieveReviews(productId));
        return cfProductInfo.thenCombine(cfReview, (productInfo, review) -> new Product(productId, productInfo, review))
                .join();
    }

    private List<ProductOption> updateInventoryCFInstructor(ProductInfo productInfo) {
        List<CompletableFuture<ProductOption>> productOptionList = productInfo.getProductOptions()
                .stream()
                .map(productOption -> CompletableFuture.supplyAsync(() -> inventoryService.retrieveInventory(productOption))
                        .thenApply(inventory -> {
                            productOption.setInventory(inventory);
                            return productOption;
                        }))
                .collect(Collectors.toList());

        return productOptionList.stream().map(CompletableFuture::join).collect(Collectors.toList());
    }
}
