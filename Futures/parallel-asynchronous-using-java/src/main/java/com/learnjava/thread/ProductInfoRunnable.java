package com.learnjava.thread;

import com.learnjava.domain.ProductInfo;
import com.learnjava.service.ProductInfoService;

public class ProductInfoRunnable implements Runnable {
    private final ProductInfoService productInfoService;
    private final String productId;
    private ProductInfo productInfo;

    public ProductInfoRunnable(final ProductInfoService productInfoService, final String productId) {
        this.productInfoService = productInfoService;
        this.productId = productId;
    }

    @Override
    public void run() {
        this.productInfo = productInfoService.retrieveProductInfo(productId); // blocking call
    }

    public ProductInfo getProductInfo() {
        return productInfo;
    }
}
