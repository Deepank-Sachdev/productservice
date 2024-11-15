package com.example.productservice.models;

import lombok.Getter;
import lombok.Setter;

import java.security.PrivateKey;
@Getter
@Setter

public class Product {
    private Long id;
    private String title;
    private String description;
    private Double price;
    private String imageURL;
    private Category category;

}
