package com.java.test.billing;

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

    public String getDescription() {
        return description;
    }
}
