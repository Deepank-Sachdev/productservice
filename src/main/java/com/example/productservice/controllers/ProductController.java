package com.example.productservice.controllers;

import com.example.productservice.dtos.CreateProductRequestDto;
import com.example.productservice.models.Category;
import com.example.productservice.models.Product;
import com.example.productservice.services.FakeStoreProductService;
import com.example.productservice.services.ProductService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    private ProductService productService;

    public ProductController(@Qualifier("fakeStoreProductService") ProductService productService) {
        this.productService = productService;
    }
//    ProductService productService = new FakeStoreProductService();

    @GetMapping("/products")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/products-limit")
    public List<Product> getProductsByLimit(@RequestParam("limit") Integer limit) {
        return productService.getProductsByLimit(limit);
    }

    @GetMapping("/products-sort")
    public List<Product> getProductsBySort(@RequestParam("sort") String sort) {
        return productService.getProductsBySort(sort);
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

    @GetMapping("/categories")
    public List<String> getAllCategory() {
        return productService.getAllCategory();
    }

    @GetMapping("/categories/{category}")
    public List<Product> getProductsByCategory(@PathVariable String category) {
        return productService.getProductsByCategory(category);
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody CreateProductRequestDto requestDto) {
        Product product = productService.updateProductDetails(id,
                requestDto.getTitle(),
                requestDto.getDescription(),
                requestDto.getImage(),
                requestDto.getPrice(),
                requestDto.getCategory()
        );
        return ResponseEntity.ok(product);
    }
    @DeleteMapping("/products/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable Long id) {
        Product deletedProduct = productService.deleteProduct(id);
        if (deletedProduct != null) {
            return ResponseEntity.ok(deletedProduct);
        } else {
            // If product was not found, return a 404 response (you can customize this as needed)
            throw new RuntimeException("Product with id " + id + " not found.");
        }
    }
}
