package com.java.test.billing;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class BillTest {
    private final Bill bill = new Bill();

    @Test
    public void successfullyAddBillingRow() {
        //WHEN
        boolean isAdded = bill.addBillingRow(new BillingRow(1, "description"));

        //THEN
        assertTrue(isAdded);
    }

    @Test
    public void successfullyAddDiscountBillingRow() {
        //WHEN
        boolean isAdded = bill.addBillingRow(new DiscountBillingRow(1, "description"));

        //THEN
        assertTrue(isAdded);
    }

    @Test
    public void successfullyGetTotalAmount() {
        //GIVEN
        bill.addBillingRow(new BillingRow(2, "description"));
        bill.addBillingRow(new DiscountBillingRow(1, "description"));

        //WHEN
        double totalAmount = bill.getTotalAmount();
        //THEN
        assertThat(totalAmount, is(1D));
    }

    @Test
    public void shouldRoundTotalAmountToTwoDecimalPlaces() {
        //GIVEN
        bill.addBillingRow(new BillingRow(2.1234, "description"));

        //WHEN
        double totalAmount = bill.getTotalAmount();

        //THEN
        assertThat(totalAmount, is(2.12D));
    }
}