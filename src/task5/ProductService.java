package task5;

import task5.Product;
import task5.ProductRepository;

import java.util.List;

public class ProductService {
    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public void addProduct(Product product) {
        repository.addProduct(product);
    }

    public List<Product> listProducts() {
        return repository.getAllProducts();
    }

    public void removeProduct(int id) {
        repository.removeProduct(id);
    }
}