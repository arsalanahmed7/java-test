package com.java.test.billing;

import static java.lang.String.format;

public class BillingRow {
    private double amount;
    private String description;

    public BillingRow(final double amount, final String description) {
        this.amount = amount;
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return format("%s ------- %s", description, amount);
    }
}
