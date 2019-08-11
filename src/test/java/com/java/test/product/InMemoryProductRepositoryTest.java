package com.java.test.product;

import org.junit.Test;

import static java.lang.String.format;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public class InMemoryProductRepositoryTest {

    private final InMemoryProductRepository repository = new InMemoryProductRepository();
    @Test
    public void successfullySaveProduct() {
        //GIVEN
        Product product = new Product("productName", "unit", 1);

        //WHEN
        boolean isAdded = repository.save(product);

        //THEN
        assertThat(isAdded, is(true));
    }

    @Test
    public void successfullySaveMultipleProduct() {
        //GIVEN
        Product product = new Product("productName", "unit", 1);
        Product product2 = new Product("productName2", "unit", 1);

        //WHEN
        boolean isAdded = repository.save(product, product2);

        //THEN
        assertThat(isAdded, is(true));
    }

    @Test(expected = ProductAlreadyExistException.class)
    public void cannotAddProductWithSameName() {
        //GIVEN
        Product product = new Product("productName", "unit", 1);
        Product product2 = new Product("productName", "unit", 1);

        //WHEN
        try {
            repository.save(product, product2);
            fail();
        } catch (Exception ex) {
            assertThat(ex.getMessage(), is(format("Product %s already exists", product2.getName())));
            throw ex;
        }

        //THEN
    }



    @Test
    public void returnProductFromRepository() {
        //GIVEN
        Product product = new Product("productName", "unit", 1);
        repository.save(product);

        //WHEN
        Product returnedProduct = repository.getProductByName("productName");

        //THEN
        assertThat(returnedProduct, is(product));
    }

    @Test(expected = ProductNotFoundException.class)
    public void returnExceptionWhenProductIsNotFoundInRepository() {
        //GIVEN
        String productName = "anyOtherProductName";

        //WHEN
        Product returnedProduct = null;
        try{
            returnedProduct = repository.getProductByName(productName);
            fail();
        } catch (Exception ex) {
            assertThat(returnedProduct, is(nullValue()));
            assertThat(ex.getMessage(), is(format("Unable to find product %s", productName)));
            throw ex;
        }

    }
}
