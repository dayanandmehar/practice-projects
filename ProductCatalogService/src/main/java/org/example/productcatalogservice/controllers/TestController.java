package org.example.productcatalogservice.controllers;

import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test")
    public void throwException() {
        throw new RuntimeException("Something went bad");
    }
}
