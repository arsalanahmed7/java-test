package com.java.test.product;

public interface ProductRepository {
    void save(Product product);
    void save(Product... product);
    Product getProductByName(String name);
}
