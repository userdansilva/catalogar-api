package com.catalogar.product;

import jakarta.websocket.server.PathParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAll() {
        List<Product> products = productService.getAll();

        return ResponseEntity.ok().body(products);
    }

    @GetMapping("{id}")
    public ResponseEntity<Optional<Product>> getById(
            @PathVariable("id") UUID id
    ) {
        Optional<Product> product = productService.getById(id);

        return ResponseEntity.ok().body(product);
    }

    @PostMapping
    public ResponseEntity<Product> create(
            @RequestBody Product product
    ) {
        Product newProduct = productService.create(product);

        return ResponseEntity.ok().body(newProduct);
    }

    @PutMapping("{id}")
    public ResponseEntity<Product> update(
            @PathVariable("id") UUID id,
            @RequestBody Product product
    ) {
        Product updatedProduct = productService.update(id, product);

        return ResponseEntity.ok().body(updatedProduct);
    }

    @DeleteMapping("{id}")
    public void delete(
            @PathParam("id") UUID id
    ) {
        productService.delete(id);
    }
}
