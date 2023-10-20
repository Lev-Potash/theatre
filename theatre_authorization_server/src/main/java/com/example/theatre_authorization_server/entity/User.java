package com.example.theatre_authorization_server.entity;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.*;
//import javax.persistence.*;
import java.util.Arrays;
import java.util.Collection;

@Entity
@Data
@Table(name = "users")
@NoArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String username;
    private String password;

    private String role;
//    @Column(name = "full_name")
//    private String fullName;
//    @Column(name = "phone_number")
//    private String phoneNumber;




//    public User toUser(PasswordEncoder encoder/*, String password*/) {
//        return new User(id, username, encoder.encode(password), fullName, phone);
//    }


//    public User(String username, String password, String fullName, String phoneNumber) {
//        this.username = username;
//        this.password = password;
//        this.fullName = fullName;
//        this.phoneNumber = phoneNumber;
//    }


    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"),
                new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
