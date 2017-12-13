package com.calculator.application;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    @RequestMapping(value = "/")
    public String index() {
    	return "Greetings from Spring Boot!";
    }

}
