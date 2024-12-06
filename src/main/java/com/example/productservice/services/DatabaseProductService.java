package com.example.productservice.services;

import com.example.productservice.models.Category;
import com.example.productservice.models.Product;
import com.example.productservice.repositories.CategoryRepository;
import com.example.productservice.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service ("databaseProductService")
public class DatabaseProductService implements ProductService {

    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;
    public DatabaseProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Product getProductDetails(Long id) { //* add throws pnfe
//         toDo - add null check and throw error
        if (productRepository.findById(id).isEmpty()) {
            // throw product not found error/exception
            return null;
        }
        return productRepository.findById(id).get();
    }

    @Override
    public Product createProduct(String title, String description, String image, double price, String category) {
        Product product = new Product();
        product.setTitle(title);
        product.setDescription(description);
        product.setImageURL(image);
        product.setPrice(price);

        Category categoryFromDatabase = categoryRepository.findByName(category);
        if (categoryFromDatabase == null) {
            Category newCategory = new Category();
            newCategory.setName(category);

            categoryFromDatabase = newCategory;
//            categoryFromDatabase =  categoryRepository.save(newCategory);
        }
        product.setCategory(categoryFromDatabase);
        return productRepository.save(product);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByLimit(Integer limit) {
        return null;
    }

    @Override
    public List<Product> getProductsBySort(String sort) {
        return null;
    }

    @Override
    public List<String> getAllCategory() {
        return null;
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return null;
    }

    @Override
    public Product updateProductDetails(Long id, String title, String description, String image, double price, String category) {
        return null;
    }

    @Override
    public Product deleteProduct(Long id) {
        return null;
    }
}
