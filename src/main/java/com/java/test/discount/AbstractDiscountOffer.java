package com.java.test.discount;

import com.java.test.billing.Basket;
import com.java.test.billing.DiscountBillingRow;
import com.java.test.product.Product;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

public abstract class AbstractDiscountOffer implements DiscountOffer {
    private final LocalDateTime startsFrom;
    private final LocalDateTime endDate;

    AbstractDiscountOffer(LocalDateTime startsFrom, LocalDateTime endDate) {
        this.startsFrom = startsFrom;
        this.endDate = endDate;
    }

    @Override
    public Optional<DiscountBillingRow> apply(final Basket basket, final Map<String, Product> products) {
        if(!doesApply(basket) || products.isEmpty()) {
            return Optional.empty();
        }

        double totalDiscount = calculateDiscount(basket, products);

        if(totalDiscount > 0D) {
            return Optional.of(new DiscountBillingRow(roundTo2DecimalPlaces(totalDiscount), getDescription()));
        } else {
            return Optional.empty();
        }
    }

    protected abstract double calculateDiscount(Basket basket, Map<String, Product> products);

    protected abstract String getDescription();

    private double roundTo2DecimalPlaces(double totalDiscount) {
        return Math.floor(totalDiscount * 100) / 100;
    }

    private boolean doesApply(Basket basket) {
        return startsFrom.isBefore(basket.getBillDate()) && basket.getBillDate().isBefore(endDate);
    }
}
