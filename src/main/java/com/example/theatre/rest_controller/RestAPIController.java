package com.example.theatre.rest_controller;

import com.example.theatre.entity.Client;
import com.example.theatre.service.ClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/rest-api-clients", produces = {"application/json"/*, "text/xml"*/})
@CrossOrigin(origins = {"https://localhost:8443", "https://localhost:9090"})
public class RestAPIController {

    private ClientService clientService;

    @Autowired
    public RestAPIController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping(params = "recent")
    public List<Client> getRecentClients() {
        Pageable pageable = PageRequest.of(0, 10,  Sort.by("id").descending());
        return clientService.findAllClients(pageable).getContent();
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Client processCreateOfClient(@RequestBody Client client) {
        Client savedClient = clientService.save(client);
        log.warn("savedClient {}", savedClient );
        return savedClient;
    }

    @PutMapping(path = "/{clientId}", consumes = "application/json")
    public Client putClient(@PathVariable("clientId") Long clientId,
                            @RequestBody Client client
    ) {
        client.setId(clientId);
        return clientService.save(client);
    }

    @PatchMapping(path = "/{clientId}", consumes = "application/json")
    public Client patchClient(
            @PathVariable("clientId") Long clientId,
            @RequestBody Client patchClient
    ) {
        Client client = clientService.findById(clientId).get();
        log.warn("patch client {}", client);
        if (!patchClient.getName().equals("")) {
            client.setName(patchClient.getName());
        }
        if (!patchClient.getSurname().equals("")) {
            client.setSurname(patchClient.getSurname());
        }
        if (!patchClient.getEmail().equals("")) {
            client.setEmail(patchClient.getEmail());
        }
        log.warn("saved patch client {}", client);
        return clientService.save(client);
    }

    @DeleteMapping(path = "/{clientId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteClient(@PathVariable("clientId") Long clientId) {
        clientService.deleteById(clientId);
    }

    @PostMapping("/test-request")
    public ResponseEntity<String> testPostRequest() {
        return ResponseEntity.ok("POST request successful");
    }
}
