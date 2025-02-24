package com.example.productservice.services;

import com.example.productservice.models.Category;
import com.example.productservice.models.Product;
import com.example.productservice.repositories.CategoryRepository;
import com.example.productservice.repositories.ProductRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service ("databaseProductService")
public class DatabaseProductService implements ProductService {

    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;
    private RedisTemplate <Long,Object> redisTemplate;

    public DatabaseProductService(ProductRepository productRepository, CategoryRepository categoryRepository,
                                    RedisTemplate<Long, Object> redisTemplate) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Product getProductDetails(Long id) { //* add throws pnfe
//         toDo - add null check and throw error
        Product productFromCache = (Product) redisTemplate.opsForValue().get(id);
        if (productFromCache != null) {
            return productFromCache;
        }

        if (productRepository.findById(id).isEmpty()) {
            // throw product not found error/exception
            return null;
        }
        redisTemplate.opsForValue().set(id, productRepository.findById(id).get());
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
        return productRepository.findAll(PageRequest.of(0, limit)).getContent();
    }

    @Override
    public List<Product> getProductsBySort(String sort) {
        if (sort.equalsIgnoreCase("desc")) {
            return productRepository.findAll(Sort.by(sort).descending());
        }
        return productRepository.findAll(Sort.by(sort).ascending());
    }

    @Override
    public List<String> getAllCategory() {
        return categoryRepository.findDistinctByNameNotNull();
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        if (category == null || category.trim().isEmpty()) {
            throw new IllegalArgumentException("Category cannot be null or empty");
        }
        return productRepository.findByCategoryNameIgnoreCase(category);
    }

    @Override
    public Product updateProductDetails(Long id, String title, String description, String image, double price, String category) throws Exception {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isEmpty()) {
            throw new Exception("Product not found");
        }

        Product product = productOptional.get();
        product.setTitle(title);
        product.setDescription(description);
        product.setImageURL(image);
        product.setPrice(price);

        if (category != null) {
            Category categoryFromDatabase = categoryRepository.findByName(category);

            if (categoryFromDatabase == null) {
                Category newCategory = new Category();
                newCategory.setName(category);
                categoryFromDatabase = newCategory;
            }

            product.setCategory(categoryFromDatabase);
        }
        else {
            product.setCategory(product.getCategory());
        }

        return productRepository.save(product);
    }

    @Override
    public Product deleteProduct(Long id) throws Exception {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()){
            Product product = productOptional.get();
            productRepository.delete(product);
            return product;
        }
        else{
            throw new Exception("Product not found");
        }
    }
}
