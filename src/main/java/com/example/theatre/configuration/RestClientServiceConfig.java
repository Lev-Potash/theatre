//package com.example.theatre.configuration;
//
//import com.example.theatre.rest_service.RestClientService;
//import com.example.theatre.service.ClientService;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
//import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
//import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
//import org.springframework.web.context.annotation.RequestScope;
//
//@Configuration
//public class RestClientServiceConfig {
//
//    // bean компонент принадлежащий области видимости запроса
//    // компонет должен получать информацию из SecurityContext информацией об аутентификации
//    // который заполняется при каждом запросе фильтром spring security
//    // но эта информация отсутсвует при запуске приложения и появлется только при выполнении запроса
//    @Bean
//    @RequestScope // bean компонент принадлежащий области видимости запроса
//    public ClientService clientService(OAuth2AuthorizedClientService clientService) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        String accessToken = null;
//
//        // если есть токен в заголовке authentication
//        if (authentication.getClass().isAssignableFrom(OAuth2AuthenticationToken.class)) {
//            OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
//            String clientRegistrationId = oauthToken.getAuthorizedClientRegistrationId();
//
//            if (clientRegistrationId.equals("theatre-admin-client")) {
//                OAuth2AuthorizedClient client = clientService.loadAuthorizedClient(
//                        clientRegistrationId, oauthToken.getName()
//                );
//                accessToken = client.getAccessToken().getTokenValue();
//            }
//        }
//        return new RestClientService(accessToken);
//    }
//}
