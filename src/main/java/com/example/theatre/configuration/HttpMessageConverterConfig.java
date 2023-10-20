package com.example.theatre.configuration;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class HttpMessageConverterConfig {

//    @Bean
//    SourceHttpMessageConverter httpMessageConverter() throws IOException {
//        FormHttpMessageConverter converter = new FormHttpMessageConverter();
//        converter.readInternal(String.class, null);
//        return converter;
//    }


//    @Bean
//    HttpMessageConverter httpMessageConverter() throws IOException {
//        FormHttpMessageConverter converter = new FormHttpMessageConverter();
//        converter.addPartConverter(new MappingJackson2HttpMessageConverter());
//        converter.addPartConverter(new FormHttpMessageConverter());
//        return converter;
//    }


    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        jsonConverter.setObjectMapper(objectMapper);
        return jsonConverter;
    }

//    @Bean
//    List<HttpMessageConverter<?>> getFormMessageConverters() {
//        List<HttpMessageConverter<?>> converters = new ArrayList<>();
//        converters.add(new FormHttpMessageConverter());
//        return converters;
//    }
}
