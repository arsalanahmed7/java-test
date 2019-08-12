package com.java.test.discount;

import com.java.test.billing.Basket;
import com.java.test.billing.DiscountBillingRow;
import com.java.test.product.Product;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class MultiBuyDiscountOffer extends AbstractDiscountOffer {
    private final String productName;
    private final int buyQuantity;
    private final String discountedProductName;
    private final int discountPercent;


    public MultiBuyDiscountOffer(final String productName, final int buyQuantity, final String discountedProductName, final int discountPercent, final LocalDateTime startsFrom, final LocalDateTime endDate) {
        super(startsFrom, endDate);
        this.productName = productName;
        this.buyQuantity = buyQuantity;
        this.discountedProductName = discountedProductName;
        this.discountPercent = discountPercent;
    }

    @Override
    public double calculateDiscount(final Basket basket, final Map<String, Product> products) {

        final Integer quantityOfPromotionProduct = basket.getProductQuantityByName(productName);
        final Integer quantityOfDiscountedProduct = basket.getProductQuantityByName(discountedProductName);
        final Product discountProduct = products.get(discountedProductName);
        final double totalDiscountNumbers = Math.floor(quantityOfPromotionProduct / buyQuantity);
        final double totalDiscountedProductNumber = quantityOfDiscountedProduct;
        final double percent = (double) discountPercent/100;
        final double discountedProducts = Math.min(totalDiscountNumbers, totalDiscountedProductNumber);
        return discountedProducts * percent * discountProduct.getCost();
    }

    @Override
    protected String getDescription() {
        return "Multi buy offer discount";
    }

    @Override
    public List<String> promotionInvolvedProductNames() {
        return Arrays.asList(productName, discountedProductName);
    }


}
