package task5;

import task5.Product;

public class ProductFactory {
    private static int counter = 1;

    public static Product createProduct(String name, double price) {
        return new Product(counter++, name, price);
    }
}