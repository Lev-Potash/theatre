package com.example.theatre_authorization_server.coniguration;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
//import org.springframework.security.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.web.SecurityFilterChain;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

@Configuration(proxyBeanMethods = false)
public class AuthorizationServerConfig {

    /**
     * Настройка поведения сервера авторизации OAuth2 и страницы входа по умолчанию
     *
     *
     * @return
     * @throws Exception
     */
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE) // высокий приоритет. выполниться этот метод тк он приоритетный
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
        return http
                .formLogin(Customizer.withDefaults())
                .build();
    }

    // http://localhost:9000/oauth2/authorize?response_type=code&client_id=theatre-admin-client&redirect_uri=http://localhost:9090/login/oauth2/code/theatre-admin-client&scope=writeClients+deleteClients+updateClients
    @Bean
    RegisteredClientRepository registeredClientRepository(PasswordEncoder passwordEncoder) {
        RegisteredClient registeredClient = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("theatre-admin-client")
                .clientSecret(passwordEncoder.encode("secret"))
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .redirectUri("http://localhost:9090/login/oauth2/code/theatre-admin-client")
                .scope("writeClients")
                .scope("deleteClients")
                .scope("updateClients")
                .scope(OidcScopes.OPENID)
                .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
//               .clientSettings(clientSettings -> clientSettings.requireUserConsent(true)) // требуется согдасие пользователя
                .build();
        return new InMemoryRegisteredClientRepository(registeredClient);
    }

    // обмениваем полученный код авторизации на токен доступа
    /*
    curl http://localhost:9000/oauth2/token \
    -H"Content-Type: application/x-www-form-urlencoded" \
    -d"grant_type=authorization_code" \
    -d"redirect_uri=http://localhost:9090/login/oauth2/code/theatre-admin-client" \
    -d"code=o2dIWrVx0z9KGGWvRW8Eu9t-CKaMi56VlHFxfDarwSCqC8feFcThgPwarLAnlzErPrJCBdQ9sRXJXnlRMy6SiT8R_bEVXtKsAJSlZMozIpTENnKPfsd1B2ddZkcd64av" \
    -u theatre-admin-client:secret

    curl.exe http://localhost:9000/oauth2/token -H "Content-Type: application/x-www-form-urlencoded" -d"grant_type=authorization_code" -d"redirect_uri=http://localhost:9090/login/oauth2/code/theatre-admin-client" -d"code=$code" -u"theatre-admin-client:secret"

    curl.exe http://localhost:9000/oauth2/token -H "Content-Type: application/x-www-form-urlencoded" -d"grant_type=authorization_code" -d"redirect_uri=http://localhost:9090/login/oauth2/code/theatre-admin-client" -d"code=IV_lkKQxUzoUjELFaggGvw95fOt_J_Xc3H37S4HiGQzXeSK7tgerewIJdhE4Vmd1z3s4fWsjY3f4XuB4u6AXiMXuU_3atqZ4vzd1dirz61j7UYcfXntfssQwfGZscd-n" -u"theatre-admin-client:secret"

    */

    /*
    curl http://localhost:9090/rest-api-clients \
    -H"Content-type: application/json" \
    -H"Authorization: Bearer $token" \
    -d'{"id":"1000", "name":"Karl", "surname":"Perashkin", "email":"karl@leon.ru"}'

    curl.exe http://localhost:9090/rest-api-clients -H"Content-type: application/json" -d'{"id":"1000", "name":"Karl", "surname":"Perashkin", "email":"karl@leon.ru"}'


    curl http://localhost:9090/rest-api-clients \
    -H"Content-type: application/json" \
    -H"Authorization: Bearer $token" \
    -d'{"id":"1000", "name":"Karl", "surname":"Perashkin", "email":"karl@leon.ru"}'

    // POST запрос с токеном
    curl.exe http://localhost:9090/rest-api-clients -H"Content-type: application/json" -H"Authorization: Bearer $token" -d'{\"name\":\"Karl\", \"surname\":\"Perashkin\", \"email\":\"karl@leon.ru\"}'

    curl.exe http://localhost:9090/rest-api-clients -H"Content-type: application/json" -H"Authorization: Bearer eyJraWQiOiI0ZTU3NTRmMy0yNTU4LTQwYjEtYmQ0Ny02MDNhODIwZDg0OGUiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImF1ZCI6InRoZWF0cmUtYWRtaW4tY2xpZW50IiwibmJmIjoxNjkzMjMyOTQ0LCJzY29wZSI6WyJkZWxldGVDbGllbnRzIiwid3JpdGVDbGllbnRzIiwidXBkYXRlQ2xpZW50cyJdLCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjkwMDAiLCJleHAiOjE2OTMyMzMyNDQsImlhdCI6MTY5MzIzMjk0NH0.jQGhLT4Pe2JQW8ixqzvsH-p3AIYwNJ7HFnlWHYRH1JpqO_ymVu-U-vqZzeFo8FPzJ5xUvR0gSslgQJbNz_BeJ4SwIgIhFLCgj3GD-K4Ysj80YbTQJDqp-IbsRyNwwmgMFVJvCpaxBiZCIN8haIuySaONBq7v_eeHjJaVoEswGJy6tZiBdGCw1Ih7WPFsvhAIeEoYIhMJ1KRzakL40LjhKHzKXZTyLIT2IaFUS20f0Zol4qw9JIT-X-pmPR2lvpH8zgAtP-7CpCtK1d0H4xfvpixvX9PndCBIjkESIG6mVxYogwISQC4zj_5DB1etTEvqV5q67kWr-hpgHqNhr6anRw" -d'{\"name\":\"Karl\", \"surname\":\"Perashkin\", \"email\":\"karl@leon.ru\"}'

// POST запрос без токена
curl.exe http://localhost:9090/rest-api-clients -H"Content-type: application/json" -d'{\"name\":\"Karl\", \"surname\":\"Perashkin\", \"email\":\"karl@leon.ru\"}'

    */

    /*
     "refresh_token":"jCZVzUn0d0bf9WSfEI--R2QXfO1TWWw0MXNm3sRWD26gUxJLgCoVJaUkIUwZfOg1kNb9uCycziCzLNQyywbkUcAQJrf3Ty2K7UPJ0f-QspfKdI67egM9cdoDTQzj__IG"

     curl http://9000/oauth2/token \
     -H"Content-type: application/x-www-form-urlencoded" \
     -d"grant_type=refresh_token&refresh_token=$refresh_token" \
     -u"theatre-admin-client:secret"

// запрос на обновление токена
     curl.exe http://9000/oauth2/token -H"Content-type: application/x-www-form-urlencoded" -d"grant_type=refresh_token&refresh_token=jCZVzUn0d0bf9WSfEI--R2QXfO1TWWw0MXNm3sRWD26gUxJLgCoVJaUkIUwZfOg1kNb9uCycziCzLNQyywbkUcAQJrf3Ty2K7UPJ0f-QspfKdI67egM9cdoDTQzj__IG" -u"theatre-admin-client:secret"


     */

    @Bean
    JWKSource<SecurityContext> jwkSource() throws NoSuchAlgorithmException {
        RSAKey rsaKey = generateRsa();
        JWKSet jwkSet = new JWKSet(rsaKey);
        return ((jwkSelector, securityContext) -> jwkSelector.select(jwkSet));
    }

    /**
     * Get public and private keys and build RSA key
     * @return
     * @throws NoSuchAlgorithmException
     */
    private static RSAKey generateRsa() throws NoSuchAlgorithmException {
        KeyPair keyPair = generateRsaKey();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        return new RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .keyID(UUID.randomUUID().toString())
                .build();
    }

    /**
     * Create pair 2048 bit keys RSA, which will used to signature token
     *
     * @return
     * @throws NoSuchAlgorithmException
     */
    private static KeyPair generateRsaKey() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        return keyPairGenerator.generateKeyPair();
    }

    @Bean
    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
    }

}
