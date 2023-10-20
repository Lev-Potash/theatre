package com.example.theatre.entity;

import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
public class RegistrationForm {
    private String username;
    private String password;
    private String fullName;
    private String phoneNumber;

    public User toUser(PasswordEncoder encoder) {
        return new User(username, encoder.encode(password), fullName, phoneNumber);
    }
}
