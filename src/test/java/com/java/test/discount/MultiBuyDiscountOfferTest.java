package com.java.test.discount;

import com.google.common.collect.ImmutableMap;
import com.java.test.billing.Basket;
import com.java.test.billing.DiscountBillingRow;
import com.java.test.product.Product;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MultiBuyDiscountOfferTest {

    private static final DiscountBillingRow EMPTY_DISCOUNT_BILLING_ROW = new DiscountBillingRow(0, "");
    private final DiscountOffer multiBuyDiscount = new MultiBuyDiscountOffer("productName", 1, "productName1", 50, LocalDateTime.now().minusDays(2), LocalDateTime.now().plusDays(7));

    @Test
    public void shouldApplyDiscount() {
        //GIVEN
        final Basket basket = mock(Basket.class);

        when(basket.getProductQuantityByName("productName")).thenReturn(1);
        when(basket.getProductQuantityByName("productName1")).thenReturn(1);
        //WHEN
        final Optional<DiscountBillingRow> discountBillingRow = multiBuyDiscount.apply(basket, ImmutableMap.of("productName", new Product("productName", "unit", 1), "productName1", new Product("productName1", "unit", 1)));

        assertThat(discountBillingRow.orElse(EMPTY_DISCOUNT_BILLING_ROW).getAmount(), is(-0.5D));
    }

    @Test
    public void shouldApplyDiscountWhenOtherProductIs100PercentOff() {
        DiscountOffer multiBuyDiscount = new MultiBuyDiscountOffer("productName", 1, "productName1", 100, LocalDateTime.now().minusDays(2), LocalDateTime.now().plusDays(7));

        //GIVEN
        final Basket basket = mock(Basket.class);

        when(basket.getProductQuantityByName("productName")).thenReturn(1);
        when(basket.getProductQuantityByName("productName1")).thenReturn(1);
        //WHEN
        final Optional<DiscountBillingRow> discountBillingRow = multiBuyDiscount.apply(basket, ImmutableMap.of("productName", new Product("productName", "unit", 1), "productName1", new Product("productName1", "unit", 1)));

        assertThat(discountBillingRow.orElse(EMPTY_DISCOUNT_BILLING_ROW).getAmount(), is(-1D));
    }

    @Test
    public void shouldNotApplyDiscountWhenPromotionIsExpired() {
        //GIVEN
        DiscountOffer multiBuyDiscount = new MultiBuyDiscountOffer("productName", 1, "productName1", 50, LocalDateTime.now().minusDays(2), LocalDateTime.now().plusDays(-1));

        final Basket basket = mock(Basket.class);

        //WHEN
        final Optional<DiscountBillingRow> discountBillingRow = multiBuyDiscount.apply(basket, ImmutableMap.of("productName", new Product("productName", "unit", 1), "productName1", new Product("productName1", "unit", 1)));

        assertFalse(discountBillingRow.isPresent());
    }

    @Test
    public void shouldNotApplyDiscountWhenDiscountAmountIsZero() {
        //GIVEN
        DiscountOffer multiBuyDiscount = new MultiBuyDiscountOffer("productName", 1, "productName1", 50, LocalDateTime.now().minusDays(2), LocalDateTime.now().minusDays(-1));

        final Basket basket = mock(Basket.class);

        //WHEN
        final Optional<DiscountBillingRow> discountBillingRow = multiBuyDiscount.apply(basket, ImmutableMap.of("productName", new Product("productName", "unit", 1), "productName1", new Product("productName1", "unit", 0)));

        assertFalse(discountBillingRow.isPresent());
    }

}