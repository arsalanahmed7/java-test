package com.java.test.discount;

import com.java.test.billing.Basket;
import com.java.test.billing.DiscountBillingRow;
import com.java.test.product.Product;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.lang.String.format;
import static java.util.Collections.singletonList;

public class FlatPercentDiscountOffer extends AbstractDiscountOffer {
    private final String productName;
    private final double discountPercent;

    public FlatPercentDiscountOffer(final String productName, final double discountPercent, final LocalDateTime offerStartsFrom, final LocalDateTime offerExpiresAt) {
        super(offerStartsFrom, offerExpiresAt);
        this.productName = productName;
        this.discountPercent = discountPercent;
    }

    @Override
    public double calculateDiscount(final Basket basket, final Map<String, Product> products) {
        final Integer productQuantityByName = basket.getProductQuantityByName(productName);
        final Product product = products.get(productName);
        final double percent = discountPercent /100;
        return  product.getCost() * percent * productQuantityByName;
    }

    @Override
    public List<String> promotionInvolvedProductNames() {
        return singletonList(productName);
    }

    @Override
    protected String getDescription() {
        return format("%s discount on %s", discountPercent + "%", productName);
    }
}
