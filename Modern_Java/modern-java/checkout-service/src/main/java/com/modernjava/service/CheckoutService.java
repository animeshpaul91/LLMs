package com.modernjava.service;

import com.modernjava.domain.*;
import com.modernjava.payment.PaymentService;

public class CheckoutService {

    private final PaymentService paymentService;

    public CheckoutService(final PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    public CheckOutStatus checkoutOrder(final OrderDetails orderDetails) {
        final var paymentResponse = paymentService.makePayment(orderDetails);

        if (paymentResponse.equals(PaymentResponse.SUCCESS)) {
            return CheckOutStatus.SUCCESS;
        }
        return CheckOutStatus.FAILURE;

    }
}
