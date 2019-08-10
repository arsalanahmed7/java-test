package com.java.test.acceptace.test;

import com.java.test.billing.Basket;
import com.java.test.billing.Bill;
import com.java.test.billing.CheckoutService;
import com.java.test.billing.StoreCheckoutService;
import com.java.test.discount.*;
import com.java.test.product.InMemoryProductRepository;
import com.java.test.product.Product;
import com.java.test.product.ProductRepository;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class OrderTest {
    private ProductRepository productRepository = new InMemoryProductRepository();
    private DiscountRepository discountRepository = new InMemoryDiscountRepository();
    private CheckoutService checkoutService = new StoreCheckoutService(productRepository, discountRepository);

    @Before
    public void setup(){
        Product product1 = new Product("soup", "tin",0.65);
        Product product2 = new Product("bread", "loaf", 0.8);
        Product product3 = new Product("milk", "bottle", 1.3);
        Product product4 = new Product("apples", "single",0.1);
        productRepository.save(product1, product2, product3, product4);

        DiscountOffer flatDiscount = new FlatPercentDiscountOffer("apple", 10, LocalDate.now().minusDays(2), 7);
        DiscountOffer multiBuyDiscount = new MultiBuyDiscountOffer("soup", 2, "bread", 50, LocalDate.now().minusDays(2), 7);
        discountRepository.add(flatDiscount, multiBuyDiscount);

    }

    @Test
    public void test() {
        Basket basket = new Basket();

        basket.add("soup", 3);
        basket.add("bread", 2);

        Bill bill = checkoutService.checkout(basket);

        assertThat( bill.getTotalAmount(), is(3.15));

    }
}
