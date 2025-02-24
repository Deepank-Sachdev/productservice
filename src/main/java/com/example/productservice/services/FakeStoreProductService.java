package com.example.productservice.services;

import com.example.productservice.dtos.FakeStoreProductDto;
import com.example.productservice.models.Category;
import com.example.productservice.models.Product;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service("fakeStoreProductService")
public class FakeStoreProductService implements ProductService {

    private RestTemplate restTemplate;
    private RedisTemplate<Long,Object> redisTemplate;


    public FakeStoreProductService(RestTemplate restTemplate, RedisTemplate<Long, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.restTemplate = restTemplate;
    }

    @Override
    public Product getProductDetails(Long id) {
//        FakeStoreProductDto responseDto =
//                restTemplate.getForObject("https://fakestoreapi.com/products/" + id,
//                        FakeStoreProductDto.class);
//        return responseDto != null ? responseDto.toProduct() : null;

        Product productFromCache = (Product) redisTemplate.opsForValue().get(id);
        if (productFromCache != null) {
            return productFromCache;
        }

        ResponseEntity<FakeStoreProductDto> responseDto = restTemplate.getForEntity(
                "https://fakestoreapi.com/products/" + id, FakeStoreProductDto.class);

        if (responseDto.getStatusCode() == HttpStatusCode.valueOf(404)){
            //Show error
        } else if (responseDto.getStatusCode() == HttpStatusCode.valueOf(500)) {
            // Error
        }
        redisTemplate.opsForValue().set(id, responseDto.getBody().toProduct());
        return responseDto.getBody().toProduct();
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

    @Override
    public List<Product> getProductsByLimit(Integer limit) {
        FakeStoreProductDto[] responseDto = restTemplate.getForObject("https://fakestoreapi.com/products?limit="
                                                + limit, FakeStoreProductDto[].class);
        List<Product> products = new ArrayList<>();
        for (FakeStoreProductDto productDto : responseDto) {
            products.add(productDto.toProduct());
        }
        return products;
    }

    @Override
    public List<Product> getProductsBySort(String sort) {
        FakeStoreProductDto[] responseDto = restTemplate.getForObject("https://fakestoreapi.com/products?sort="
                + sort, FakeStoreProductDto[].class);
        List<Product> products = new ArrayList<>();
        for (FakeStoreProductDto productDto : responseDto) {
            products.add(productDto.toProduct());
        }
        return products;
    }

    @Override
    public List<String> getAllCategory() {
        List<String> responseDto =
                restTemplate.getForObject("https://fakestoreapi.com/products/categories",
                List.class);
//        List<Category> categories = new ArrayList<>();
//        for (FakeStoreProductDto productDto : responseDto) {
//            categories.add(productDto.toCategory());
//        }
        return responseDto;
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        FakeStoreProductDto[] responseDto =
                restTemplate.getForObject("https://fakestoreapi.com/products/category/"
                + category, FakeStoreProductDto[].class);
        List<Product> products = new ArrayList<>();
        for (FakeStoreProductDto productDto : responseDto) {
            products.add(productDto.toProduct());
        }
        return products;
    }

    @Override
    public Product updateProductDetails(Long id, String title, String description, String image, double price, String category) {
        FakeStoreProductDto updateDto = new FakeStoreProductDto();
        updateDto.setId(id);
        updateDto.setTitle(title);
        updateDto.setDescription(description);
        updateDto.setImage(image);
        updateDto.setPrice(String.valueOf(price));
        updateDto.setCategory(category);

//        FakeStoreProductDto createDTO = restTemplate.postForObject("https://fakestoreapi.com/products/"
//                + id, updateDto, FakeStoreProductDto.class);


//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Content-Type", "application/json");

        HttpEntity<FakeStoreProductDto> entity = new HttpEntity<>(updateDto);

        // Use RestTemplate to send the PUT request and receive the response
        FakeStoreProductDto createDto = restTemplate.exchange("https://fakestoreapi.com/products/"
                + id , HttpMethod.PUT, entity, FakeStoreProductDto.class).getBody();

        return createDto != null ? createDto.toProduct() : null;
    }

    @Override
    public Product deleteProduct(Long id) {
//        FakeStoreProductDto fetchDto = restTemplate.getForObject("https://fakestoreapi.com/products)"
//                + id, FakeStoreProductDto.class);
//        if (fetchDto != null) {
          try {
              ResponseEntity<FakeStoreProductDto> responseEntity =  restTemplate.exchange("https://fakestoreapi.com/products/"
                      + id, HttpMethod.DELETE, null, FakeStoreProductDto.class);
              return responseEntity.getBody() != null ? responseEntity.getBody().toProduct() : null;
          } catch (HttpClientErrorException e) {
              throw new RuntimeException("Product not found or failed to delete: " + e.getMessage());
          }
//                  restTemplate.exchange("https://fakestoreapi.com/products/" + fetchDto.getId());
//        }
//        return
    }
}
