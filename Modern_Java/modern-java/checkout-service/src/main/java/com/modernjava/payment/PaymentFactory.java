package com.modernjava.payment;

import com.modernjava.domain.CardType;

public class PaymentFactory {
    // Factory Pattern
    public static PaymentGateway provide(CardType cardType) {
        return switch (cardType) {
            case CREDIT -> new CreditCardPayment();
            case DEBIT -> new DebitCardPayment();
            case REWARDS -> new RewardsCardPayment();
            case null -> throw new IllegalArgumentException("Card type null not supported");
        };
    }
}
