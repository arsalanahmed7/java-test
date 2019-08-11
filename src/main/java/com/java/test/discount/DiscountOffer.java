package com.java.test.discount;

import com.java.test.billing.Basket;
import com.java.test.billing.DiscountBillingRow;
import com.java.test.product.Product;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface DiscountOffer {

    Optional<DiscountBillingRow> apply(final Basket basket, final Map<String, Product> products);

    List<String> promotionInvolvedProductNames();
}
