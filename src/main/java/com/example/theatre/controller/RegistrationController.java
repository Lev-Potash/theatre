package com.example.theatre.controller;

import com.example.theatre.entity.RegistrationForm;
import com.example.theatre.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("/register")
public class RegistrationController {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String registerForm(Model model) {
        RegistrationForm registrationForm = new RegistrationForm();
        model.addAttribute("registrationForm", registrationForm);
        model.addAttribute("pageTitle", "Регистрация пользователя");
        return "registration";
    }

    @PostMapping
    public String processRegistration(@Valid RegistrationForm registrationForm) {
        log.info("Username {} Password {}",registrationForm.toUser(passwordEncoder).getUsername(), registrationForm.toUser(passwordEncoder).getPassword());
        userRepository.save(registrationForm.toUser(passwordEncoder));
        return "redirect:/login";
    }
}
