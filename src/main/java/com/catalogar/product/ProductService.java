package com.catalogar.product;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public Optional<Product> getById(UUID id) {
        return productRepository.findById(id);
    }

    public Product create(Product product) {
        return productRepository.save(product);
    }

    public Product update(UUID id, Product product) {
        Product updatedProduct = new Product();

        updatedProduct.setId(id);
        updatedProduct.setName(product.getName());

        return productRepository.save(updatedProduct);
    }

    public void delete(UUID id) {
        productRepository.deleteById(id);
    }
}
