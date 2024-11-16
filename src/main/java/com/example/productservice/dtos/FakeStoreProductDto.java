package com.example.productservice.dtos;

import com.example.productservice.models.Category;
import com.example.productservice.models.Product;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FakeStoreProductDto {
    private Long id;
    private String title;
    private String price;
    private String category;
    private String description;
    private String image;

    public Product toProduct() {
        Product product = new Product();
        product.setId(getId());
        product.setDescription(getDescription());
        product.setTitle(getTitle());
        product.setImageURL(getImage());
        product.setPrice(Double.valueOf(getPrice()));

        Category category = new Category();
        category.setName(getCategory());
        product.setCategory(category);

        return product;
    }
}
