package com.java.test.discount;

import com.java.test.billing.Basket;
import com.java.test.billing.DiscountBillingRow;
import com.java.test.product.Product;
import com.java.test.product.ProductRepository;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PromotionServiceTest {
    private final DiscountRepository discountRepository = mock(DiscountRepository.class);
    private final ProductRepository productRepository = mock(ProductRepository.class);
    private final PromotionService promotionService = new PromotionService(discountRepository, productRepository);

    @Test
    public void shouldApplyDiscount() {
        //GIVEN
        final Basket basket = mock(Basket.class);
        final MultiBuyDiscountOffer multiBuyDiscountOffer = mock(MultiBuyDiscountOffer.class);
        final List<DiscountOffer> discountOffers = singletonList(multiBuyDiscountOffer);
        when(discountRepository.getAllDiscountOffers()).thenReturn(discountOffers);
        when(multiBuyDiscountOffer.promotionInvolvedProductNames()).thenReturn(singletonList("productName"));
        final Product product = new Product("productName", "unit", 1);
        when(productRepository.getProductByName("productName")).thenReturn(product);
        final DiscountBillingRow discountBillingRow = new DiscountBillingRow(1, "description");
        when(multiBuyDiscountOffer.apply(any(), any())).thenReturn(Optional.of(discountBillingRow));
        //WHEN
        final List<DiscountBillingRow> discountBillingRows = promotionService.apply(basket);

        //THEN
        assertThat(discountBillingRows.size(), is(1));
        assertThat(discountBillingRows.get(0), is(discountBillingRow));
    }

    @Test
    public void shouldNotApplyDiscountWhenThereAreNoDiscounts() {
        //GIVEN
        final Basket basket = mock(Basket.class);
        final List<DiscountOffer> discountOffers = emptyList();
        when(discountRepository.getAllDiscountOffers()).thenReturn(discountOffers);
        //WHEN
        final List<DiscountBillingRow> discountBillingRows = promotionService.apply(basket);

        //THEN
        assertThat(discountBillingRows.size(), is(0));
    }

}