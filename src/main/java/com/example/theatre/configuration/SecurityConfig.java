package com.example.theatre.configuration;

import com.example.theatre.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

// @EnableWebSecurity используется для включения SpringSecurity в нашем проекте.
// @EnableGlobalMethodSecurity(prePostEnabled = true) нужна для применения в классах
// @PreAuthorize, @PostAuthorize, @PreFilter, и @PostFilter
// авторизовать вызовы методов, включая входные параметры и возвращаемые значения.
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig /*extends WebSecurityConfigurerAdapter*/ {
    // bean компонент применяемый для шифрования
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // хранилище данных о пользователях в памяти
//    @Profile("dev")
//    @Bean
//    public UserDetailsService userDetailsService(PasswordEncoder encoder) {
//        List<UserDetails> usersList = new ArrayList<>();
//        usersList.add(new User("buzz", encoder.encode("password"),
//                Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")))); // пользователь, пароль и список привелегий
//        usersList.add(new User("woody", encoder.encode("password"),
//                Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"))));
//        return new InMemoryUserDetailsManager(usersList);
//    }


//    @Profile("prod")
    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return username -> {
            com.example.theatre.entity.User user = userRepository.findByUsername(username);
            if (user != null) return user;
            throw new UsernameNotFoundException("User '" + username + "' not found");
        };
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.authorizeRequests()
//                .antMatchers(HttpMethod.POST, "/rest-api-clients").hasAuthority("SCOPE_writeClients")
//                .antMatchers(HttpMethod.PUT, "/rest-api-clients").hasAuthority("SCOPE_updateClients")
//                .antMatchers(HttpMethod.DELETE, "/rest-api-clients").hasAuthority("SCOPE_deleteClients")
                .antMatchers("/admin").access("hasRole('USER')")
                .antMatchers("/","/**").access("permitAll()")
                .and()
//                .oauth2ResourceServer(oauth2 -> oauth2.jwt()) // влючение поддержки токенов для определения разрешений в scope из токена
                .formLogin()
                    .loginPage("/login")
                    .defaultSuccessUrl("/") // если пользователь сразу попал на страницу входа, то после входа его перенаправит на "/"
//                .defaultSuccessUrl("/",true) // если нужно всегда попадать на данную страницу по умолчанию
                .and()
                .logout()
                    .logoutSuccessUrl("/")
                .and()
                .csrf().disable() // отключаем защиту, тк в заголоках к rest запросам post отсутстыует заголовок csrf
                .build();
    }

    // если использовать наследуемый класс WebSecurityConfigurerAdapter
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests()
//                .antMatchers(HttpMethod.POST, "/rest-api-clients").hasAuthority("SCOPE_writeClients")
//                .antMatchers(HttpMethod.PUT, "/rest-api-clients").hasAuthority("SCOPE_updateClients")
//                .antMatchers(HttpMethod.DELETE, "/rest-api-clients").hasAuthority("SCOPE_deleteClients")
//                .and()
//                .oauth2ResourceServer(oauth2 -> oauth2.jwt());
//    }

    /**
     *
     * @param http
     * @return
     * @throws Exception
     */
//    @Bean
//    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests(authorizeRequests -> authorizeRequests.anyRequest().authenticated())
//                .oauth2Login(
//                        oauth2Login ->
//                                // эта страница авторизации, которая принимает код авторизации
//                                // и обменивает его на токен доступа для идентификации пользователя
//                                oauth2Login.loginPage("/oauth2/authorization/theatre-admin-client")
//                )
//                .oauth2Client(Customizer.withDefaults());
//        return http.build();
//    }
}

