package com.java.test.billing;

import java.util.ArrayList;
import java.util.List;

public class Bill {
    private final List<BillingRow> billingRows = new ArrayList<>();
    public double getTotalAmount() {
        final double sum = billingRows.stream().mapToDouble(BillingRow::getAmount).sum();
        return Math.floor(sum *100)/100;
    }

    public boolean addBillingRow(final BillingRow billingRow) {
        return billingRows.add(billingRow);
    }
}
