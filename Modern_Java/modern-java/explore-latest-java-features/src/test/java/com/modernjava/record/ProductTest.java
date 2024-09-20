package com.modernjava.record;

import com.modernjava.records.Product;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProductTest {

    private static final Logger LOG = LoggerFactory.getLogger(ProductTest.class);

    @Test
    void createProduct() {
        // given
        final var name = "IPhone";
        final var cost = new BigDecimal("999.999");
        final var type = "Electronics";
        final Product product = new Product(name, cost, type);
        // when
        LOG.info(() -> "product = " + product);
        assertEquals(name, product.name());
        assertEquals(cost, product.cost());
        assertEquals(type, product.type());
    }

    @Test
    void createProductNameNotValid() {
        // given
        final var cost = new BigDecimal("999.999");
        final var type = "Electronics";

        // when
        final var exception = assertThrows(IllegalArgumentException.class,
                () -> new Product("", cost, type));

        // then
        assertEquals("name is invalid", exception.getMessage());
    }

    @Test
    void createProductCostNotValid() {
        // given
        final var name = "IPhone";
        final var cost = new BigDecimal("-999.999");
        final var type = "Electronics";

        // when
        final var exception = assertThrows(IllegalArgumentException.class,
                () -> new Product(name, cost, type));

        // then
        assertEquals("cost is invalid", exception.getMessage());
    }

    @Test
    void createProductCustomConstructor() {
        // given
        final var name = "IPhone";
        final var cost = new BigDecimal("999.999");
        final Product product = new Product(name, cost);
        // when
        LOG.info(() -> "product = " + product);
        assertEquals("General", product.type());
    }

    @Test
    void testEquality() {
        // given
        final var name = "IPhone";
        final var cost = new BigDecimal("999.999");
        final var type = "Electronics";

        // when
        final Product product1 = new Product(name, cost, type);
        final Product product2 = new Product(name, cost, type.concat("1"));

        // then
        assertEquals(product1, product2);
    }
}