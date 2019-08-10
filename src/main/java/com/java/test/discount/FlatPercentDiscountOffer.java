package com.java.test.discount;

import java.time.LocalDate;

public class FlatPercentDiscountOffer extends DiscountOffer {
    private final String productName;
    private final double discountPercent;
    private final LocalDate offerStartsFrom;
    private final int offerEndsInDays;

    public FlatPercentDiscountOffer(String productName, double discountPercent, LocalDate offerStartsFrom, int offerEndsInDays) {
        this.productName = productName;
        this.discountPercent = discountPercent;
        this.offerStartsFrom = offerStartsFrom;
        this.offerEndsInDays = offerEndsInDays;
    }
}
