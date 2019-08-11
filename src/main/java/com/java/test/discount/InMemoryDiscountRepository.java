package com.java.test.discount;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InMemoryDiscountRepository implements DiscountRepository {

    private List<DiscountOffer> inMemoryDiscountOffers = new ArrayList<>();

    @Override
    public boolean add(final DiscountOffer... discountOffers) {
        return inMemoryDiscountOffers.addAll(Arrays.asList(discountOffers));
    }

    @Override
    public List<DiscountOffer> getAllDiscountOffers() {
        return inMemoryDiscountOffers;
    }
}
