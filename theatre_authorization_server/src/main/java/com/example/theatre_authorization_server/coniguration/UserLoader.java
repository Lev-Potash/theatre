package com.example.theatre_authorization_server.coniguration;

import com.example.theatre_authorization_server.entity.User;
import com.example.theatre_authorization_server.repository.UserRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class UserLoader {

    @Bean
    public ApplicationRunner userDataLoader(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            userRepository.save(new User("admin", passwordEncoder.encode("password"), "ROLE_ADMIN"));
            userRepository.save(new User("user", passwordEncoder.encode("password"), "ROLE_ADMIN"));
        };
    }
}
