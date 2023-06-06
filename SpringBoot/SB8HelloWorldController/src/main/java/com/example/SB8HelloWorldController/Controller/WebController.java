package com.example.SB8HelloWorldController.Controller;

import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WebController {
    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("hello")
    public String hello(@RequestParam(name = "name", required = false, defaultValue = "") String name, @RequestParam(name = "doi", required = false, defaultValue = "") String doi, Model model, ServerProperties.Reactive.Session session) {
        model.addAttribute("name", name);
        model.addAttribute("doi", doi);
        return "hello";
    }
}
