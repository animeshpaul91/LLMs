package com.learnjava.service;

import com.learnjava.domain.checkout.Cart;
import com.learnjava.domain.checkout.CartItem;
import com.learnjava.domain.checkout.CheckoutResponse;
import com.learnjava.domain.checkout.CheckoutStatus;

import java.util.List;
import java.util.stream.Collectors;

import static com.learnjava.util.CommonUtil.*;
import static com.learnjava.util.LoggerUtil.log;

public class CheckoutService {
    private static final PriceValidatorService priceValidatorService = new PriceValidatorService();

    public CheckoutResponse checkout(Cart cart) {
        startTimer();
        List<CartItem> priceValidationList = cart.getCartItemList().parallelStream()
                .peek(cartItem -> {
                    boolean isCartItemInvalid = priceValidatorService.isCartItemInvalid(cartItem);
                    cartItem.setExpired(isCartItemInvalid);
                }).filter(CartItem::isExpired)
                .collect(Collectors.toList());

        timeTaken();
        stopWatchReset();
        // double finalPrice = calculateFinalPriceWithoutReduce(cart);
        double finalPrice = calculateFinalPriceWithReduce(cart);
        log("Checkout complete and final price is: " + finalPrice);
        return (priceValidationList.size() > 0) ? new CheckoutResponse(CheckoutStatus.FAILURE, priceValidationList) : new CheckoutResponse(CheckoutStatus.SUCCESS, finalPrice);
    }

    private double calculateFinalPriceWithoutReduce(Cart cart) {
        return cart.getCartItemList().parallelStream()
                .map(cartItem -> cartItem.getQuantity() * cartItem.getRate()).mapToDouble(Double::doubleValue).sum();
    }

    private double calculateFinalPriceWithReduce(Cart cart) {
        return cart.getCartItemList().parallelStream()
                .map(cartItem -> cartItem.getQuantity() * cartItem.getRate()).mapToDouble(Double::doubleValue).reduce(1.0, Double::sum);
    }
}
