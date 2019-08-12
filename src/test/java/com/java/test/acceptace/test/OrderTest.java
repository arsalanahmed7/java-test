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

import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class OrderTest {
    @Test
    public void shouldApplyDiscountWhenBasketHas3SoupsAnd2Breads() {
        Basket basket = new Basket();

        basket.add("soup", 3);
        basket.add("bread", 2);

        Bill bill = checkoutService.checkout(basket);

        assertThat( bill.getTotalAmount(), is(3.15));
    }

    @Test
    public void shouldApplyDiscountWhenBasketHas6ApplesAndAMilk() {
        Basket basket = new Basket();

        basket.add("apple", 6);
        basket.add("milk", 1);

        Bill bill = checkoutService.checkout(basket);

        assertThat( bill.getTotalAmount(), is(1.9));
    }

    @Test
    public void shouldApplyDiscountWhenBasketHas6ApplesAndAMilkIn5DaysTime() {
        Basket basket = new Basket(LocalDateTime.now().plusDays(5));

        basket.add("apple", 6);
        basket.add("milk", 1);

        Bill bill = checkoutService.checkout(basket);

        assertThat( bill.getTotalAmount(), is(1.84));
    }

    @Test
    public void shouldApplyDiscountWhenBasketHas3Apples2TinOfSoupAndABreadIn5DaysTime() {
        Basket basket = new Basket(LocalDateTime.now().plusDays(5));

        basket.add("apple", 3);
        basket.add("soup", 2);
        basket.add("bread", 1);

        Bill bill = checkoutService.checkout(basket);

        assertThat( bill.getTotalAmount(), is(1.97));
    }

    private void initDiscounts() {
        final DiscountOffer flatDiscount = new FlatPercentDiscountOffer("apple", 10,
                LocalDateTime.now().plusDays(3), LocalDateTime.now().plusDays(10));
        final DiscountOffer multiBuyDiscount = new MultiBuyDiscountOffer("soup", 2, "bread", 50,
                LocalDateTime.now().minusDays(2), LocalDateTime.now().plusMonths(1).with(TemporalAdjusters.lastDayOfMonth()));
        discountRepository.add(flatDiscount, multiBuyDiscount);
    }

    private void initProducts() {
        final Product soup = new Product("soup", "tin",0.65);
        final Product bread = new Product("bread", "loaf", 0.8);
        final Product milk = new Product("milk", "bottle", 1.3);
        final Product apple = new Product("apple", "single",0.1);
        productRepository.save(soup, bread, milk, apple);
    }

    @Before
    public void setup(){
        initProducts();
        initDiscounts();
    }

    private final DiscountRepository discountRepository = new InMemoryDiscountRepository();
    private final ProductRepository productRepository = new InMemoryProductRepository();
    private final PromotionService promotionService = new PromotionService(discountRepository, productRepository);
    private final CheckoutService checkoutService = new StoreCheckoutService(productRepository, promotionService);
}
