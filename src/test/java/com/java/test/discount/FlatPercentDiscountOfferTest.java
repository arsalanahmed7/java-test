package com.java.test.discount;

import com.java.test.billing.Basket;
import com.java.test.billing.DiscountBillingRow;
import com.java.test.product.Product;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

public class FlatPercentDiscountOfferTest {

    private final FlatPercentDiscountOffer flatPercentDiscountOffer = new FlatPercentDiscountOffer("apple", 10, LocalDateTime.now().minusDays(2), LocalDateTime.now().plusDays(4));
    @Test
    public void applyDiscountOnDiscountedItem() {
        //GIVEN
        Basket basket = new Basket();
        basket.add("apple", 1);
        //WHEN
        final Optional<DiscountBillingRow> billingRow = flatPercentDiscountOffer.apply(basket, Collections.singletonMap("apple", new Product("apple", "single", 0.1D)));

        //THEN
        assertThat(billingRow.orElse(new DiscountBillingRow(0, "")).getAmount(), is(-0.01D));
    }

    @Test
    public void shouldNotApplyDiscountWhenPromotionIsExpired() {
        //GIVEN
        Basket basket = new Basket(LocalDateTime.now().plusDays(6));
        basket.add("apple", 1);
        //WHEN
        final Optional<DiscountBillingRow> billingRow = flatPercentDiscountOffer.apply(basket, Collections.singletonMap("apple", new Product("apple", "single", 0.1D)));

        //THEN
        assertFalse(billingRow.isPresent());
    }

    @Test
    public void shouldNotApplyDiscountWhenProductsListIsEmpty() {
        //GIVEN
        Basket basket = new Basket(LocalDateTime.now().plusDays(3));
        basket.add("apple", 1);
        //WHEN
        final Optional<DiscountBillingRow> billingRow = flatPercentDiscountOffer.apply(basket, Collections.emptyMap());

        //THEN
        assertFalse(billingRow.isPresent());
    }

    @Test
    public void shouldNotApplyDiscountWhenNoMatchingProductFoundInBasket() {
        //GIVEN
        Basket basket = new Basket(LocalDateTime.now().plusDays(3));
        basket.add("apple", 1);
        //WHEN
        final Optional<DiscountBillingRow> billingRow = flatPercentDiscountOffer.apply(basket, Collections.emptyMap());

        //THEN
        assertFalse(billingRow.isPresent());
    }
}