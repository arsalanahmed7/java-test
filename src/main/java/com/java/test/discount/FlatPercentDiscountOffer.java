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

public class FlatPercentDiscountOffer implements DiscountOffer {
    private final String productName;
    private final double discountPercent;
    private final LocalDateTime offerStartsFrom;
    private final LocalDateTime offerExpiresAt;

    public FlatPercentDiscountOffer(final String productName, final double discountPercent, final LocalDateTime offerStartsFrom, final LocalDateTime offerExpiresAt) {
        this.productName = productName;
        this.discountPercent = discountPercent;
        this.offerStartsFrom = offerStartsFrom;
        this.offerExpiresAt = offerExpiresAt;
    }

    @Override
    public Optional<DiscountBillingRow> apply(final Basket basket, final Map<String, Product> products) {
        final Integer productQuantityByName = basket.getProductQuantityByName(productName);
        if(!doesApply(basket) || products.isEmpty() || productQuantityByName == null){
            return Optional.empty();
        }
        final Product product = products.get(productName);
        final double percent = discountPercent /100;
        final double totalDiscount = product.getCost() * percent * productQuantityByName;
        if(totalDiscount > 0D) {
            return Optional.of(new DiscountBillingRow(roundTo2DecimalPlaces(totalDiscount), format(" offer, %s discount on %s", percent + "%", product.getName())));
        } else {
            return Optional.empty();
        }
    }

    private double roundTo2DecimalPlaces(double totalDiscount) {
        return Math.floor(totalDiscount * 100) / 100;
    }

    private boolean doesApply(Basket basket) {
        return offerStartsFrom.isBefore(basket.getBillDate()) && basket.getBillDate().isBefore(offerExpiresAt);

    }

    @Override
    public List<String> promotionInvolvedProductNames() {
        return singletonList(productName);
    }
}
