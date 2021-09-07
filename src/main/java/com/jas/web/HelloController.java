package com.jas.web;


import com.jas.domain.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Autowired
    private Book book;

    @RequestMapping("/say")
    public String hello() {
        return "Hello Spring Boot";
    }

    @GetMapping("/book/{id}")
    public Object getAll() {

        return book;
    }
}
