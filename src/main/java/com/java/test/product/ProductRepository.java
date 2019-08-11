package com.java.test.product;

public interface ProductRepository {
    boolean save(final Product... product);
    Product getProductByName(final String name);
}
