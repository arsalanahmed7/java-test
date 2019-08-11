package com.java.test.billing;

import com.google.common.collect.ImmutableMap;
import com.java.test.discount.PromotionService;
import com.java.test.product.Product;
import com.java.test.product.ProductRepository;
import org.junit.Test;

import static java.util.Collections.emptyMap;
import static java.util.Collections.singletonList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class StoreCheckoutServiceTest {

    private final ProductRepository productRepository = mock(ProductRepository.class);
    private final PromotionService promotionService = mock(PromotionService.class);

    private final StoreCheckoutService checkoutService = new StoreCheckoutService(productRepository, promotionService);


    @Test
    public void shouldSuccessfullyGenerateBill() {
        //GIVEN
        final Basket basket = mock(Basket.class);
        when(basket.getAllItems()).thenReturn(ImmutableMap.of("productName", 1));
        when(productRepository.getProductByName("productName")).thenReturn(new Product("productName", "unit", 1));

        //WHEN
        final Bill bill = checkoutService.checkout(basket);

        //THEN
        verify(productRepository).getProductByName("productName");
        verify(promotionService).apply(basket);

        assertThat(bill.getTotalAmount(), is(1D));
    }

    @Test
    public void shouldSuccessfullyGenerateBillByApplyingDiscounts() {
        //GIVEN
        final Basket basket = mock(Basket.class);
        when(basket.getAllItems()).thenReturn(ImmutableMap.of("productName", 1));
        when(productRepository.getProductByName("productName")).thenReturn(new Product("productName", "unit", 1));
        when(promotionService.apply(basket)).thenReturn(singletonList(new DiscountBillingRow(0.2, "discount")));

        //WHEN
        final Bill bill = checkoutService.checkout(basket);

        //THEN
        verify(productRepository).getProductByName("productName");
        verify(promotionService).apply(basket);

        assertThat(bill.getTotalAmount(), is(0.8D));
    }

    @Test
    public void shouldSuccessfullyGenerateBillWithNoAmountWhenBasketIsEmpty() {
        //GIVEN
        final Basket basket = mock(Basket.class);
        when(basket.getAllItems()).thenReturn(emptyMap());

        //WHEN
        final Bill bill = checkoutService.checkout(basket);

        //THEN
        verify(productRepository, times(0)).getProductByName("productName");
        verify(promotionService).apply(basket);

        assertThat(bill.getTotalAmount(), is(0.0D));
    }
}