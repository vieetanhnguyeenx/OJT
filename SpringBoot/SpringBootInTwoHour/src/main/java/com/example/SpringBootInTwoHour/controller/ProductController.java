package com.example.SpringBootInTwoHour.controller;

import com.example.SpringBootInTwoHour.model.Product;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/Products")
public class ProductController {
    @GetMapping
    List<Product> getAllProduct() {
       List<Product> products = new ArrayList<>();
       products.add(new Product(1, "doi den", 100));
       products.add(new Product(2, "doi buon", 300));
       products.add(new Product(3, "rice", 500));
       products.add(new Product(4, "book", 200));
       products.add(new Product(5, "pen", 150));

       return products;
    }

    @PostMapping
    List<Product> getAllProduct1() {
        List<Product> products = new ArrayList<>();
        products.add(new Product(1, "doi den", 400));
        products.add(new Product(2, "doi buon", 700));
        return products;
    }
}
