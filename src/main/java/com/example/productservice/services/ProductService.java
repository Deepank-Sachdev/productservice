package com.example.productservice.services;

import com.example.productservice.models.Category;
import com.example.productservice.models.Product;

import java.util.List;

public interface ProductService {
    Product getProductDetails(Long id);
    Product createProduct(String title, String description, String image, double price, String category);
    List<Product> getAllProducts();
    List<Product> getProductsByLimit(Integer limit);
    List<Product> getProductsBySort(String sort);
    List<String> getAllCategory();
    List<Product> getProductsByCategory(String category);
    Product updateProductDetails(Long id, String title, String description, String image, double price, String category);
    Product deleteProduct(Long id);
}
