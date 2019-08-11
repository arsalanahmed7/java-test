package com.java.test.product;

public class ProductNotFoundException extends RuntimeException {
    ProductNotFoundException(String msg) {
        super(msg);
    }
}
