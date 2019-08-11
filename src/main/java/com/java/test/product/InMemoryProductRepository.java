package com.java.test.product;

import java.util.*;

import static java.lang.String.format;
import static java.util.Arrays.stream;

public class InMemoryProductRepository implements ProductRepository {

    private final Map<String, Product> inMemoryProducts = new HashMap<>();
    @Override
    public boolean save(final Product... products) {
        stream(products).forEach(product -> {
            if(inMemoryProducts.get(product.getName()) != null) {
                throw new ProductAlreadyExistException(format("Product %s already exists", product.getName()));
            }
            inMemoryProducts.put(product.getName(), product);
        });
        return true;
    }

    @Override
    public Product getProductByName(final String name) {
        Product product = inMemoryProducts.get(name);
        return Optional.ofNullable(product).orElseThrow(() ->new ProductNotFoundException(format("Unable to find product %s", name)));
    }
}
