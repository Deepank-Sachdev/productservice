package com.example.productservice.services;

import com.example.productservice.dtos.FakeStoreProductDto;
import com.example.productservice.models.Product;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

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
        
        return responseDto != null ? responseDto.toProduct() : null;
    }

    @Override
    public Product createProduct(String title, String description, String image,
                                 double price, String category) {
        FakeStoreProductDto createDto = new FakeStoreProductDto();
        createDto.setTitle(title);
        createDto.setDescription(description);
        createDto.setImage(image);
        createDto.setCategory(category);
        createDto.setPrice(String.valueOf(price));

        FakeStoreProductDto responseDto =
                restTemplate.postForObject("https://fakestoreapi.com/products", createDto,
                        FakeStoreProductDto.class);
        return responseDto != null ? responseDto.toProduct() : null;
    }

    @Override
    public List<Product> getAllProducts() {
        FakeStoreProductDto[] responseDto =
                restTemplate.getForObject("https://fakestoreapi.com/products",
                        FakeStoreProductDto[].class);

        List<Product> products = new ArrayList<>();
        for (FakeStoreProductDto productDto : responseDto) {
            products.add(productDto.toProduct());
        }
        return products;
    }
}
