package com.modernjava.service;

import com.modernjava.domain.Card;
import com.modernjava.domain.CardType;
import com.modernjava.domain.CheckOutStatus;
import com.modernjava.domain.OrderDetails;
import com.modernjava.payment.PaymentService;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CheckoutServiceTest {
    private final PaymentService paymentService = new PaymentService();
    private final CheckoutService checkoutService = new CheckoutService(paymentService);

    private static OrderDetails orderDetails(CardType cardType) {
        var card = new Card("ABC", "7676709809809809",
                "4567", "09/99", cardType);
        return new OrderDetails("1234", card, 99.0);
    }

    @ParameterizedTest(name = "Checkout for CardType: {0}")
    @EnumSource(CardType.class)
    void testCheckout(final CardType cardType) {
        final var orderDetails = orderDetails(cardType);
        final var response = checkoutService.checkoutOrder(orderDetails);
        assertEquals(CheckOutStatus.SUCCESS, response);
    }
}
