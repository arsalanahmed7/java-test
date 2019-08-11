package com.java.test.product;

public class ProductAlreadyExistException extends RuntimeException {
    ProductAlreadyExistException(String msg) {
        super(msg);
    }
}
