package com.java.test;

import com.java.test.billing.Basket;
import com.java.test.billing.Bill;
import com.java.test.billing.CheckoutService;
import com.java.test.billing.StoreCheckoutService;
import com.java.test.discount.*;
import com.java.test.product.InMemoryProductRepository;
import com.java.test.product.Product;
import com.java.test.product.ProductRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Scanner;

import static java.lang.String.format;

public class MainApplication {

    private static final String SOUP = "soup";
    private static final String BREAD = "bread";
    private static final String MILK = "milk";
    private static final String APPLE = "apple";

    public static void main(String[] args) {
        final ProductRepository productRepository = new InMemoryProductRepository();
        final DiscountRepository discountRepository = new InMemoryDiscountRepository();
        final PromotionService promotionService = new PromotionService(discountRepository, productRepository);
        final CheckoutService checkoutService = new StoreCheckoutService(productRepository, promotionService);
        initProducts(productRepository);
        initPromotions(discountRepository);
        Scanner scanner = new Scanner(System.in);

        LocalDate date = LocalDate.now();
        try {
            System.out.println("Enter billing date fin format(yyyy-MM-dd):");
            date = parseDate(scanner.next());

        } catch (Exception e) {
            System.out.println("unable to parse date, setting todays date for bill");
        }

        final Basket basket = new Basket(LocalDateTime.of(date, LocalTime.MIN));
        addProductsIntoBasket(scanner, basket, APPLE);
        addProductsIntoBasket(scanner, basket, MILK);
        addProductsIntoBasket(scanner, basket, SOUP);
        addProductsIntoBasket(scanner, basket, BREAD);

        final Bill bill = checkoutService.checkout(basket);

        System.out.println(bill.toString());
        System.out.println("\nTotal amount: " + bill.getTotalAmount());
    }

    private static LocalDate parseDate(String date) {
        //default, ISO_LOCAL_DATE "2016-08-12"
        return LocalDate.parse(date);

    }

    private static void addProductsIntoBasket(Scanner scanner, Basket basket, String productName) {
        System.out.println(format("Enter amount of %s", productName));
        basket.add(productName, scanner.nextInt());
    }

    private static void initPromotions(DiscountRepository discountRepository) {
        final DiscountOffer flatDiscount = new FlatPercentDiscountOffer("apple", 10,
                LocalDateTime.now().plusDays(3), LocalDateTime.now().plusDays(10));
        final DiscountOffer multiBuyDiscount = new MultiBuyDiscountOffer("soup", 2, "bread", 50,
                LocalDateTime.now().minusDays(2), LocalDateTime.now().plusMonths(1).with(TemporalAdjusters.lastDayOfMonth()));
        discountRepository.add(flatDiscount, multiBuyDiscount);
    }

    private static void initProducts(ProductRepository productRepository) {
        final Product soup = new Product(SOUP, "tin",0.65);
        final Product bread = new Product(BREAD, "loaf", 0.8);
        final Product milk = new Product(MILK, "bottle", 1.3);
        final Product apple = new Product(APPLE, "single",0.1);
        productRepository.save(soup, bread, milk, apple);
    }
}
