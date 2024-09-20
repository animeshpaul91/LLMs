package com.modernjava.payment;

import com.modernjava.domain.OrderDetails;
import com.modernjava.domain.PaymentResponse;

public class PaymentService {
    public PaymentResponse makePayment(OrderDetails orderDetails) {
        //implement a payment gateway that can handle the different kinds of payment.
        final var card = orderDetails.card();
        final var amount = orderDetails.finalAmount();
        final PaymentGateway paymentGateway = PaymentFactory.provide(card.cardType());
        return paymentGateway.makePayment(card, amount);
    }
}
