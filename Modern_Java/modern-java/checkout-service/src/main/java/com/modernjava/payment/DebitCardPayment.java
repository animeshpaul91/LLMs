package com.modernjava.payment;

import com.modernjava.domain.Card;
import com.modernjava.domain.PaymentResponse;

public final class DebitCardPayment extends PaymentGateway {
    @Override
    public PaymentResponse makePayment(Card card, double amount) {
        final String message = "Acquire debit card payment for amount %s".formatted(amount);
        System.out.println(message);
        return PaymentResponse.SUCCESS;
    }
}
