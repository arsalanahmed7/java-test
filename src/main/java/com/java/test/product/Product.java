package com.java.test.product;

public class Product {
    private final String name;
    private final double cost;
    private final String unit;

    public Product(String name, String unit, double cost) {
        this.name = name;
        this.unit = unit;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public double getCost() {
        return cost;
    }

    public String getUnit() {
        return unit;
    }
}
