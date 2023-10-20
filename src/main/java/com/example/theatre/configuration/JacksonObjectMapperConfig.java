package com.example.theatre.configuration;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class JacksonObjectMapperConfig {

    /**
     * Настраиваем объект ObjectMapper сереализатора Jackson для того чтобы исключить
     * определение Jackson двойных кавычек при задании запроса клиентским приложением (curl)
     * с передачей JSON в виде парамертра.
     * Необходимо вводить \ перед "" задании JSON параметров в curl чтобы запрос работал
     * Например
     * curl.exe http://localhost:9090/rest-clients \
     * -H"Content-type: application/json" \
     * -H"Authorization: Bearer $token" \
     * -d'{\"name\":\"Karl\", \"surname\":\"Perashkin\", \"email\":\"karl@leon.ru\"}'
     *
     * @return настроенный объект ObjectMapper
     */
    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        return new ObjectMapper()
                .configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
    }

}
