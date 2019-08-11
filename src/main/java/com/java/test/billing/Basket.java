package com.java.test.billing;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Basket {
    private final Map<String, Integer> items = new HashMap<>();

    public boolean add(final String product, final int quantity) {
        if (items.get(product) == null) {

            items.put(product, quantity);
        } else {
            Integer existingQuantity = items.get(product);
            items.put(product, existingQuantity + quantity);
        }

        return true;
    }

    public Map<String, Integer> getAllItems() {
        return items;
    }

    public Integer getProductQuantityByName(final String productName) {
        return Optional.ofNullable(items.get(productName)).orElse(0);
    }
}
