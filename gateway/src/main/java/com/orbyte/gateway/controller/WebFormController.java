package com.orbyte.gateway.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class WebFormController {

    // Handles all non-file paths (e.g. /dashboard, /users/1) → SPA routing
    @GetMapping(value = "/{path:[^\\.]*}")
    public String forward() {
        return "forward:/index.html";
    }

    // Handles root "/"
    @GetMapping(value = "/")
    public String root() {
        return "forward:/index.html";
    }
}