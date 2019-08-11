package com.java.test.discount;

import com.java.test.billing.Basket;
import com.java.test.billing.DiscountBillingRow;
import com.java.test.product.Product;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class MultiBuyDiscountOffer implements DiscountOffer {
    private final String productName;
    private final int buyQuantity;
    private final String discountedProductName;
    private final int discountPercent;
    private final LocalDate startsFrom;
    private final int forNumberOfDays;

    public MultiBuyDiscountOffer(final String productName, final int buyQuantity, final String discountedProductName, final int discountPercent, final LocalDate startsFrom, final int forNumberOfDays) {
        this.productName = productName;
        this.buyQuantity = buyQuantity;
        this.discountedProductName = discountedProductName;
        this.discountPercent = discountPercent;
        this.startsFrom = startsFrom;
        this.forNumberOfDays = forNumberOfDays;
    }

    @Override
    public Optional<DiscountBillingRow> apply(final Basket basket, final Map<String, Product> products) {
        if(!doesApply()) {
            return Optional.empty();
        }

        final Integer quantityOfPromotionProduct = basket.getProductQuantityByName(productName);
        final Integer quantityOfDiscountedProduct = basket.getProductQuantityByName(productName);
        final Product discountProduct = products.get(discountedProductName);
        final double totalDiscountNumbers = Math.floor(quantityOfPromotionProduct / buyQuantity);
        final double totalDiscountedProductNumber = Math.floor(quantityOfDiscountedProduct / buyQuantity);
        final double percent = (double) discountPercent/100;
        final double totalDiscount = totalDiscountNumbers * totalDiscountedProductNumber * percent * discountProduct.getCost();
        if(totalDiscount > 0D) {
            return Optional.of(new DiscountBillingRow(totalDiscount, "multi buy offer"));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<String> promotionInvolvedProductNames() {
        return Arrays.asList(productName, discountedProductName);
    }

    private boolean doesApply() {
        final LocalDate now = LocalDate.now();
        return startsFrom.isBefore(now) && now.isBefore(startsFrom.plusDays(forNumberOfDays));
    }
}
