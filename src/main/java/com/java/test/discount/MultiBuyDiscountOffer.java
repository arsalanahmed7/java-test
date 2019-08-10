package com.java.test.discount;

import java.time.LocalDate;

public class MultiBuyDiscountOffer extends DiscountOffer {
    private final String productName;
    private final int buyQuantity;
    private final String discountedProduct;
    private final int discountPercent;
    private final LocalDate startsFrom;
    private final int forNumberOfDays;

    public MultiBuyDiscountOffer(String productName, int buyQuantity, String discountedProduct, int discountPercent, LocalDate startsFrom, int forNumberOfDays) {
        this.productName = productName;
        this.buyQuantity = buyQuantity;
        this.discountedProduct = discountedProduct;
        this.discountPercent = discountPercent;
        this.startsFrom = startsFrom;
        this.forNumberOfDays = forNumberOfDays;
    }
}
