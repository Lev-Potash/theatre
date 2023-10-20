package com.example.theatre.controller;

import com.example.theatre.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {


    private final MessageSource messageSource;

    @Autowired
    public AdminController(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String getLocalizedMessage(String key, Object[] args) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(key, args, locale);
    }

    @GetMapping
    public String adminForm(Model model, @AuthenticationPrincipal User user) {
        String welcomeMessage = messageSource.getMessage("welcome", new Object[] {"John"}, Locale.US);
        String welcomeMessageEN = messageSource.getMessage("welcome", new Object[] {"John"}, Locale.ENGLISH);
        String welcomeMessageRU = messageSource.getMessage("welcome", new Object[] {"John"}, Locale.forLanguageTag("RU"));

        Map<String, Object> args = new HashMap<>();
        args.put("name", "John");
//        String welcomeMessageMapEN_US = messageSource.getMessage("welcome.message", new Map[]{args}, Locale.US);
//        String welcomeMessageMapEN = messageSource.getMessage("welcome.message", new Map[]{args}, Locale.ENGLISH);
//        String welcomeMessageMapRU = messageSource.getMessage("welcome.message", new Map[]{args}, Locale.forLanguageTag("RU"));

        model.addAttribute("authenticationUsername", user.getUsername());
        model.addAttribute("welcomeMessage", welcomeMessage);
        model.addAttribute("welcomeMessageEN", welcomeMessageEN);
        model.addAttribute("welcomeMessageRU", welcomeMessageRU);
//        model.addAttribute("welcomeMessageMapEN_US", welcomeMessageMapEN_US);
//        model.addAttribute("welcomeMessageMapEN", welcomeMessageMapEN);
//        model.addAttribute("welcomeMessageMapRU", welcomeMessageMapRU);
        return "admin";
    }
}
