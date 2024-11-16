package com.example.productservice.services;

import com.example.productservice.dtos.FakeStoreProductDto;
import com.example.productservice.models.Category;
import com.example.productservice.models.Product;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class FakeStoreProductService implements ProductService {

    private RestTemplate restTemplate;

    public FakeStoreProductService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Product getProductDetails(Long id) {
        FakeStoreProductDto responseDto =
                restTemplate.getForObject("https://fakestoreapi.com/products/" + id,
                        FakeStoreProductDto.class);

        Product product = new Product();
        product.setId(responseDto != null ? responseDto.getId() : null);
        product.setDescription(responseDto != null ? responseDto.getDescription() : null);
        product.setTitle(responseDto != null ? responseDto.getTitle() : null);
        product.setImageURL(responseDto != null ? responseDto.getImage() : null);
        product.setPrice(Double.valueOf(responseDto != null ? responseDto.getPrice() : null));

        Category category = new Category();
        category.setName(responseDto != null ? responseDto.getCategory() : null);
        product.setCategory(category);

        return product;
    }
}
