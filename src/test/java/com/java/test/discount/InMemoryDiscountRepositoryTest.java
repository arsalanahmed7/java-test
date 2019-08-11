package com.java.test.discount;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;


public class InMemoryDiscountRepositoryTest {

    private final InMemoryDiscountRepository repository = new InMemoryDiscountRepository();
    @Test
    public void canAddFlatDiscountOffers() {
        //WHEN
        boolean isAdded = repository.add(mock(FlatPercentDiscountOffer.class));

        //THEN
        assertTrue(isAdded);
    }

    @Test
    public void canAddMultiBuyDiscountOffers() {
        //WHEN
        boolean isAdded = repository.add(mock(MultiBuyDiscountOffer.class));

        //THEN
        assertTrue(isAdded);
    }

    @Test
    public void getAllDiscounts() {
        //GIVEN
        MultiBuyDiscountOffer discountOffer = mock(MultiBuyDiscountOffer.class);
        repository.add(discountOffer);

        //WHEN
        List<DiscountOffer> allDiscountOffers = repository.getAllDiscountOffers();

        //THEN
        assertThat(allDiscountOffers.size(), is(1));
        assertThat(allDiscountOffers.get(0), is(discountOffer));
    }
}