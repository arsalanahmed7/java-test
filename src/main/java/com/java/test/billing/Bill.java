package com.java.test.billing;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class Bill {
    private final List<BillingRow> billingRows = new ArrayList<>();
    public double getTotalAmount() {
        final double sum = billingRows.stream().mapToDouble(BillingRow::getAmount).sum();
        return Math.floor(sum *100)/100;
    }

    public boolean addBillingRow(final BillingRow billingRow) {
        return billingRows.add(billingRow);
    }

    @Override
    public String toString() {
        AtomicReference<StringBuffer> billBuffer = new AtomicReference<>(new StringBuffer());
        billingRows.forEach(billingRow-> billBuffer.get().append(billingRow.toString()).append("\n"));
        return billBuffer.toString();
    }
}
