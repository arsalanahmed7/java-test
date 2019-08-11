package com.java.test.discount;

import com.java.test.billing.Basket;
import com.java.test.billing.DiscountBillingRow;
import com.java.test.product.Product;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.Arrays.asList;
import static java.util.Collections.*;

public class FlatPercentDiscountOffer implements DiscountOffer {
    private final String productName;
    private final double discountPercent;
    private final LocalDate offerStartsFrom;
    private final int offerEndsInDays;

    public FlatPercentDiscountOffer(final String productName, final double discountPercent, final LocalDate offerStartsFrom, final int offerEndsInDays) {
        this.productName = productName;
        this.discountPercent = discountPercent;
        this.offerStartsFrom = offerStartsFrom;
        this.offerEndsInDays = offerEndsInDays;
    }


    @Override
    public Optional<DiscountBillingRow> apply(final Basket basket, final Map<String, Product> products) {
        products.get(productName);
        return Optional.empty();
    }

    @Override
    public List<String> promotionInvolvedProductNames() {
        return singletonList(productName);
    }
}
