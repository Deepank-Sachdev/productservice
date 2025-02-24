package com.example.productservice.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.security.PrivateKey;
@Getter
@Setter
@Entity
public class Product extends BaseModel implements Serializable {
    private String title;
    private String description;
    private Double price;
    private String imageURL;

    @ManyToOne (cascade = {CascadeType.PERSIST})
    private Category category;

}
