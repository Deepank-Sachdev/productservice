package com.example.productservice.controllers;

import com.example.productservice.models.Product;
import com.example.productservice.services.FakeStoreProductService;
import com.example.productservice.services.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }
//    ProductService productService = new FakeStoreProductService();

    @GetMapping("/products")
    public void getAllProducts() {

    }
    @GetMapping("/products/{id}")
    public Product getProductDetails(@PathVariable Long id) {
        productService.getProductDetails(id);
        return null;
    }

    public void createProduct(Product product) {

    }

}
