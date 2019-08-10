package com.java.test.discount;

public interface DiscountRepository {
    void add(DiscountOffer discountOffer);
    void add(DiscountOffer... discountOffer);
}
