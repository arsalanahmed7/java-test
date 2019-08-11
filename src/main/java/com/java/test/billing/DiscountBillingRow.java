package com.java.test.billing;

public class DiscountBillingRow extends BillingRow{
    public DiscountBillingRow(final double amount, final String description) {
        super(amount, description);
    }

    @Override
    public double getAmount() {
        return -super.getAmount();
    }
}
