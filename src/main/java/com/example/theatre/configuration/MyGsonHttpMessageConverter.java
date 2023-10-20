//package com.example.theatre.configuration;
//
//import org.springframework.http.MediaType;
//import org.springframework.http.converter.json.GsonHttpMessageConverter;
//
//import java.util.Arrays;
//import java.util.List;
//
//public class MyGsonHttpMessageConverter extends GsonHttpMessageConverter {
//    public MyGsonHttpMessageConverter() {
//        List<MediaType> types = Arrays.asList(
//                new MediaType("text", "html", DEFAULT_CHARSET),
//                new MediaType("application", "json", DEFAULT_CHARSET),
//                new MediaType("application", "*+json", DEFAULT_CHARSET)
//        );
//        super.setSupportedMediaTypes(types);
//    }
//}