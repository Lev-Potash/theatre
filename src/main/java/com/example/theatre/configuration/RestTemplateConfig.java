package com.example.theatre.configuration;


//import org.apache.http.client.HttpClient;
//import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
//import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
//import org.apache.http.impl.client.HttpClients;
//import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.Resource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Configuration
public class RestTemplateConfig {

    @Bean
    RestTemplate restTemplate() throws Exception {
        RestTemplate restTemplate = new RestTemplate();
//        restTemplate.setMessageConverters(getFormMessageConverters());
        return restTemplate;
    }
//
//    List<HttpMessageConverter<?>> getFormMessageConverters() {
//        List<HttpMessageConverter<?>> converters = new ArrayList<>();
//        converters.add(new FormHttpMessageConverter());
//        converters.add(new MappingJackson2HttpMessageConverter());
//        return converters;
//    }







//    @Bean
//    public RestTemplate restTemplate() {
//        final RestTemplate restTemplate = new RestTemplate();
//
//        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
//        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
//        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
//        messageConverters.add(converter);
//        restTemplate.setMessageConverters(messageConverters);
//
//        return restTemplate;
//    }







//    @Value("${server.ssl.trust-store}")
//    private Resource trustStore;
//    @Value("${server.ssl.trust-store-password}")
//    private String trustStorePassword;

//    @Bean
//    RestTemplate restTemplate() throws Exception {
//        SSLContext sslContext = new SSLContextBuilder()
//                .loadTrustMaterial(
//                        trustStore.getURL(),
//                        trustStorePassword.toCharArray()
//                ).build();
//        SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(sslContext);
//        HttpClient httpClient = HttpClients.custom()
//                .setSSLSocketFactory(socketFactory).build();
//        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
//        return new RestTemplate(factory);
//    }


//    @Bean
//    public RestTemplate restTemplate() {
//        return new RestTemplate(/*new CustomClientHttpRequestFactory(10000, 10000, true)*/);
//
////        RestTemplate restTemplate = new RestTemplate();
////        HttpClient httpClient = HttpClientBuilder.create().build();
////        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
////        restTemplate.setRequestFactory(requestFactory);
//
////        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
////        keyStore.load(new FileInputStream(new File("mykeys.jks")),
////                "123456".toCharArray());
////        SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(
////                new SSLContextBuilder()
////                        .loadTrustMaterial(null, new TrustSelfSignedStrategy())
////                        .loadKeyMaterial(keyStore, "123456".toCharArray()).build());
////        HttpClient httpClient = HttpClients.custom().setSSLSocketFactory(socketFactory).build();
////        ClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(
////                httpClient);
////        RestTemplate restTemplate = new RestTemplate(requestFactory);
////        return restTemplate;
//
////        TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;
////
////        SSLContextBuilder sslcontext = new SSLContextBuilder();
////        sslcontext.loadTrustMaterial(null, new TrustSelfSignedStrategy());
////
////        SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom()
////                .loadTrustMaterial(null, acceptingTrustStrategy)
////                .build();
////
////        SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);
////
////        CloseableHttpClient httpClient = HttpClients.custom()/*.setSSLContext(sslcontext.build())*/.setSSLHostnameVerifier(
////                        NoopHostnameVerifier.INSTANCE)
////                .build();
////
////        HttpComponentsClientHttpRequestFactory requestFactory =
////                new HttpComponentsClientHttpRequestFactory();
////
////        requestFactory.setHttpClient(httpClient);
////        RestTemplate restTemplate = new RestTemplate(requestFactory);
////        return restTemplate;
//
//
//    }

//    private static final String allPassword = "123456";
//    @Bean
//    public RestTemplate restTemplate(RestTemplateBuilder builder) throws Exception {
//
//        SSLContext sslContext = SSLContextBuilder
//                .create()
//                .loadKeyMaterial(ResourceUtils.getFile("classpath:mykeys.jks"), allPassword.toCharArray(), allPassword.toCharArray())
//                .loadTrustMaterial(ResourceUtils.getFile("classpath:mykeys.jks"), allPassword.toCharArray())
//                .build();
//
//        HttpClient client = HttpClients.custom()
//                .setSSLContext(sslContext)
//                .build();
//
//        return builder
//                .requestFactory(() -> new HttpComponentsClientHttpRequestFactory(client))
//                .build();
//    }


}
