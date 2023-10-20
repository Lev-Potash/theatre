package com.example.theatre.rest_service;

import com.example.theatre.entity.Client;
import com.example.theatre.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class RestClientService implements ClientService {

    RestTemplate restTemplate;

    @Autowired
    public RestClientService(String accessToken) {
        this.restTemplate = new RestTemplate();
        if (accessToken != null) {
            this.restTemplate
                    .getInterceptors()
                    .add(getBearerTokenInterceptor(accessToken));

        }

    }

    private ClientHttpRequestInterceptor getBearerTokenInterceptor(String accessToken) {
        ClientHttpRequestInterceptor interceptor = new ClientHttpRequestInterceptor() {
            @Override
            public ClientHttpResponse intercept(HttpRequest request, byte[] body,
                                                ClientHttpRequestExecution execution) throws IOException {
                request.getHeaders().add("Authorization", "Bearer " + accessToken);

                return execution.execute(
                        request,
                        body
                );
            }
        };
        return interceptor;
    }

    @Override
    public Optional<Client> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Page<Client> findAllClients(Pageable pageable) {
        return (Page<Client>) Arrays.asList(restTemplate.getForObject(
                "http://localhost:9090/rest-api-clients?recent", Client[].class
        ));
    }

    @Override
    public List<Client> findAllClients() {
        return null;
    }

    @Override
    public Optional<Client> findClientByTicket(Long id) {
        return Optional.empty();
    }

    @Override
    public Client save(Client client) {
        return restTemplate.postForObject(
                "http://localhost:9090/rest-api-clients",
                client,
                Client.class);
    }

    @Override
    public void deleteById(Long id) {

    }
}
