package com.java.test.billing;

import static java.lang.String.format;

public class DiscountBillingRow extends BillingRow{
    public DiscountBillingRow(final double amount, final String description) {
        super(amount, description);
    }

    @Override
    public double getAmount() {
        return -super.getAmount();
    }

    @Override
    public String toString() {
        return format("Discount: %s ------- %s", getDescription(), getAmount());
    }
}
