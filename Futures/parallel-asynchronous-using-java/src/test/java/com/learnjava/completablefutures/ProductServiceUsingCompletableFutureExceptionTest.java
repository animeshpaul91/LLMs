package com.learnjava.completablefutures;

import com.learnjava.domain.Product;
import com.learnjava.service.InventoryService;
import com.learnjava.service.ProductInfoService;
import com.learnjava.service.ReviewService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceUsingCompletableFutureExceptionTest {

    @Mock
    private ProductInfoService productInfoService;
    @Mock
    private ReviewService reviewService;
    @Mock
    private InventoryService inventoryService;

    @InjectMocks
    private ProductServiceUsingCompletableFuture productServiceUsingCompletableFuture;

    @Test
    public void retrieveProductDetailsClientWithInventoryCF() {
        // Given
        String productId = "ABC123";
        when(productInfoService.retrieveProductInfo(productId)).thenCallRealMethod();
        when(reviewService.retrieveReviews(productId)).thenThrow(new RuntimeException("Exception Occurred"));
        when(inventoryService.retrieveInventoryCF(any())).thenCallRealMethod();

        // When
        Product product = productServiceUsingCompletableFuture.retrieveProductDetailsClientWithInventoryCF(productId);

        // Then
        assertNotNull(product);
        assertTrue(product.getProductInfo().getProductOptions().size() > 0);
        assertNotNull(product.getReview());
        assertEquals(0, product.getReview().getNoOfReviews());
        assertEquals(0.0, product.getReview().getOverallRating());
        product.getProductInfo().getProductOptions().forEach(productOption -> assertNotNull(productOption.getInventory()));
    }

    @Test
    public void retrieveProductDetailsClientWithInventoryCFProductInfoServiceErrorWhenComplete() {
        // Given
        String productId = "ABC123";
        when(productInfoService.retrieveProductInfo(productId)).thenThrow(new RuntimeException("Exception Occurred"));
        when(reviewService.retrieveReviews(productId)).thenCallRealMethod();

        // Then
        Assertions.assertThrows(RuntimeException.class, () -> productServiceUsingCompletableFuture.retrieveProductDetailsClientWithInventoryCF(productId));
    }

    @Test
    public void retrieveProductDetailsClientWithInventoryServiceErrorExceptionally() {
        // Given
        String productId = "ABC123";
        when(productInfoService.retrieveProductInfo(productId)).thenCallRealMethod();
        when(reviewService.retrieveReviews(productId)).thenCallRealMethod();
        when(inventoryService.retrieveInventoryCF(any())).thenReturn(CompletableFuture.failedFuture(new RuntimeException("Exception Occurred")));

        // When
        Product product = productServiceUsingCompletableFuture.retrieveProductDetailsClientWithInventoryCF(productId);

        // Then
        assertNotNull(product);
        assertTrue(product.getProductInfo().getProductOptions().size() > 0);
        assertNotNull(product.getReview());
        product.getProductInfo().getProductOptions().forEach(productOption -> assertNotNull(productOption.getInventory()));
        product.getProductInfo().getProductOptions().forEach(productOption -> assertEquals(1, productOption.getInventory().getCount()));
    }
}