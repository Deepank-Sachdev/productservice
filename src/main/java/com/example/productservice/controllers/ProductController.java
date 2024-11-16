package com.example.productservice.controllers;

import com.example.productservice.dtos.CreateProductRequestDto;
import com.example.productservice.models.Product;
import com.example.productservice.services.FakeStoreProductService;
import com.example.productservice.services.ProductService;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }
//    ProductService productService = new FakeStoreProductService();

    @GetMapping("/products")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }
    @GetMapping("/products/{id}")
    public Product getProductDetails(@PathVariable Long id) {
        return productService.getProductDetails(id);
    }

    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody CreateProductRequestDto requestDto) {
        Product product = productService.createProduct(
                            requestDto.getTitle(),
                            requestDto.getDescription(),
                            requestDto.getImage(),
                            requestDto.getPrice(),
                            requestDto.getCategory()
                             );
        ResponseEntity<Product> responseEntity = new ResponseEntity<>(product,
                HttpStatusCode.valueOf(201));
        return responseEntity;
    }

}
