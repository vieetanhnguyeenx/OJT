package com.example.SpringBootInTwoHour.controller;

import com.example.SpringBootInTwoHour.model.Product;
import com.example.SpringBootInTwoHour.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {
//    @RequestMapping(value = "/home", method = RequestMethod.GET)
//    public ResponseEntity<List<Product>> home(Model model) {
//        List<Product> products = new ArrayList<>();
//        products.add(new Product(1, "doi den", 100));
//        products.add(new Product(2, "doi buon", 300));
//        products.add(new Product(3, "rice", 500));
//        products.add(new Product(4, "book", 200));
//        products.add(new Product(5, "pen", 150));
//
//        return new ResponseEntity<>(products, HttpStatus.OK);
//    }

    @RequestMapping("/home/login")
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login.html");
        return modelAndView;
    }

    @RequestMapping(value = "/home/login", method = RequestMethod.POST)
    public ResponseEntity<User> loginAuthentication(@RequestBody User user) {
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
