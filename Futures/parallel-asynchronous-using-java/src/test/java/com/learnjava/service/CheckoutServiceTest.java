package com.learnjava.service;

import com.learnjava.domain.checkout.Cart;
import com.learnjava.domain.checkout.CheckoutResponse;
import com.learnjava.domain.checkout.CheckoutStatus;
import com.learnjava.util.DataSet;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ForkJoinPool;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CheckoutServiceTest {
    private static final CheckoutService checkoutService = new CheckoutService();

    @Test
    public void noOfCores() {
        int numberOfCores = Runtime.getRuntime().availableProcessors();
        System.out.println("Number of Cores: " + numberOfCores);
        assertEquals(8, numberOfCores);
    }

    @Test
    public void parallelism() {
        int numberOfThreads = ForkJoinPool.getCommonPoolParallelism();
        System.out.println("Number of threads in Fork Join Pool: " + numberOfThreads);
        /* this is one less than the number of cores in the machine because the Common ForkJoin Pool involves main thread (also) into the transaction.
            In other words, the thread that initiated the computation will also be a part of the parallelism.
         */
    }

    @Test
    void checkout() {
        // Given
        Cart cart = DataSet.createCart(6);
        // when
        CheckoutResponse checkoutResponse = checkoutService.checkout(cart);
        assertEquals(CheckoutStatus.SUCCESS, checkoutResponse.getCheckoutStatus());
        assertTrue(checkoutResponse.getFinalRate() > 0);
    }

    @Test
    void checkoutMoreItems() {
        // Given
        Cart cart = DataSet.createCart(9);
        // when
        CheckoutResponse checkoutResponse = checkoutService.checkout(cart);
        assertEquals(CheckoutStatus.FAILURE, checkoutResponse.getCheckoutStatus());
    }

    @Test
    void modifyParallelism() {
        // Given
        // -Djava.util.concurrent.ForkJoinPool.common.parallelism=100
        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "100");

        Cart cart = DataSet.createCart(100);
        // when
        CheckoutResponse checkoutResponse = checkoutService.checkout(cart);
        assertEquals(CheckoutStatus.FAILURE, checkoutResponse.getCheckoutStatus());
    }
}