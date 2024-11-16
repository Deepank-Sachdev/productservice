package com.example.productservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateProductRequestDto {
    private String title;
    private String description;
    private double price;
    private String category;
    private String image;
}
