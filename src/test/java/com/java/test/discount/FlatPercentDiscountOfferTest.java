package com.java.test.discount;

import com.java.test.billing.Basket;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Collections;

public class FlatPercentDiscountOfferTest {

    private final FlatPercentDiscountOffer flatPercentDiscountOffer = new FlatPercentDiscountOffer("apple", 10, LocalDate.now().minusDays(2), 7);
    @Test
    public void applyDiscountOnDiscountedItem() {
        //GIVEN
        Basket basket = new Basket();
        basket.add("apple", 1);
        //WHEN
        flatPercentDiscountOffer.apply(basket, Collections.emptyMap());
    }
}