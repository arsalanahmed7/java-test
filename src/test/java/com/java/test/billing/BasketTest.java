package com.java.test.billing;

import org.junit.Test;

import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class BasketTest {
    private final Basket basket = new Basket();

    @Test
    public void addItemInBasket() {
        //WHEN
        boolean isAdded = basket.add("productName", 1);

        //THEN
        assertTrue(isAdded);
    }

    @Test
    public void getAllBasketItems() {
        //GIVEN
        basket.add("productName", 1);
        basket.add("productName2", 1);

        //WHEN
        Map<String, Integer> items = basket.getAllItems();
        //THEN
        assertThat(items.size(), is(2));
        assertTrue(items.containsKey("productName"));
        assertTrue(items.containsKey("productName2"));
    }

    @Test
    public void shouldAddQuantity() {
        //GIVEN
        basket.add("productName", 1);
        basket.add("productName", 1);

        //WHEN
        Map<String, Integer> items = basket.getAllItems();

        //THEN
        assertThat(items.size(), is(1));
        assertThat(items.get("productName"), is(2));
    }

    @Test
    public void shouldGetProductQuantityByName() {
        //GIVEN
        basket.add("productName", 2);

        //WHEN
        final Integer quantity = basket.getProductQuantityByName("productName");

        //THEN
        assertThat(quantity, is(2));
    }

    @Test
    public void shouldGetNoProductQuantityWhenItIsNotFound() {
        //GIVEN
        basket.add("productName", 2);

        //WHEN
        final Integer quantity = basket.getProductQuantityByName("productName2");

        //THEN
        assertThat(quantity, is(0));
    }

}