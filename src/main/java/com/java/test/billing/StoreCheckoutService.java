package com.java.test.billing;

import com.java.test.discount.DiscountRepository;
import com.java.test.product.ProductRepository;

public class StoreCheckoutService implements CheckoutService {
    public StoreCheckoutService(ProductRepository productRepository, DiscountRepository discountRepository) {

    }

    @Override
    public Bill checkout(Basket basket) {
        return null;
    }
}
