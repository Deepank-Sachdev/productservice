package com.example.productservice.controllers;

import com.example.productservice.dtos.SearchRequestDto;
import com.example.productservice.models.Product;
import com.example.productservice.services.SearchService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SearchController {

    private SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @PostMapping("/search")
    public Page<Product> search(@RequestBody SearchRequestDto requestDto) {
        return searchService.search(requestDto.getQuery(),requestDto.getPageNumber(),requestDto.getSize());

    }
}