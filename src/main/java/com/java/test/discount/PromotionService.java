package com.java.test.discount;

import com.java.test.billing.Basket;
import com.java.test.billing.BillingRow;
import com.java.test.billing.DiscountBillingRow;
import com.java.test.product.Product;
import com.java.test.product.ProductRepository;

import java.util.*;
import java.util.stream.Collectors;

public class PromotionService {
    private final DiscountRepository discountRepository;
    private final ProductRepository productRepository;

    public PromotionService(final DiscountRepository discountRepository, final ProductRepository productRepository) {
        this.discountRepository = discountRepository;
        this.productRepository = productRepository;
    }

    public List<DiscountBillingRow> apply(Basket basket) {
        List<DiscountBillingRow> discountBillingRows = new ArrayList<>();
        discountRepository.getAllDiscountOffers().forEach( discountOffer -> {
            final List<String> productNames = discountOffer.promotionInvolvedProductNames();
            Map<String, Product> products = productNames.stream().map(productRepository::getProductByName).collect(Collectors.toMap(Product::getName, product -> product));
            final Optional<DiscountBillingRow> billingRow = discountOffer.apply(basket, products);
            billingRow.ifPresent(discountBillingRows::add);
        });
        return discountBillingRows;
    }
}
