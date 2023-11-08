package com.example.theatre.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TheatreController {

    @GetMapping("/")
    public String getHome(Model model) {

        model.addAttribute("classActiveSettings","active");
        return "home";
    }

}
