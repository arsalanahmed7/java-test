package com.java.test.discount;

import java.util.List;

public interface DiscountRepository {
    boolean add(final DiscountOffer... discountOffer);
    List<DiscountOffer> getAllDiscountOffers();
}
