package com.java.test.billing;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class DiscountBillingRowTest {

    @Test
    public void shouldReturnNegativeValue() {
        //WHEN
        final DiscountBillingRow discountBillingRow = new DiscountBillingRow(1, "");

        //THEN
        assertThat(discountBillingRow.getAmount(), is(-1D));
    }
}