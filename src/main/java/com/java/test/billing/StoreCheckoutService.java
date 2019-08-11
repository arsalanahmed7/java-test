package com.java.test.billing;

import com.java.test.discount.PromotionService;
import com.java.test.product.Product;
import com.java.test.product.ProductRepository;

import java.util.List;

public class StoreCheckoutService implements CheckoutService {
    private final ProductRepository productRepository;
    private final PromotionService promotionService;

    public StoreCheckoutService(final ProductRepository productRepository, final PromotionService promotionService) {
        this.productRepository = productRepository;
        this.promotionService = promotionService;
    }

    @Override
    public Bill checkout(final Basket basket) {
        final Bill bill = new Bill();
        basket.getAllItems().forEach((productName, quantity) -> {
            final Product product = productRepository.getProductByName(productName);
            bill.addBillingRow(new BillingRow(quantity * product.getCost(), productName));

        });

        final List<DiscountBillingRow> promotions = promotionService.apply(basket);
        promotions.forEach(bill::addBillingRow);
        return bill;
    }
}
