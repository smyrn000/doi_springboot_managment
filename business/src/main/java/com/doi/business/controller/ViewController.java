package com.doi.business.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@CrossOrigin("*")
public class ViewController {

    @RequestMapping(value = "/**/{path:[^.]*}")
    public String forward(@PathVariable String path) {
        return "forward:/";
    }
}
