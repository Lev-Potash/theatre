package com.example.theatre.rest_client;

import com.example.theatre.entity.Client;
import com.example.theatre.service.ClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.Arrays;


import static de.flapdoodle.embed.process.runtime.AbstractProcess.TIMEOUT;

@Slf4j
@Controller
@SessionAttributes({"clientModel"})
@RequestMapping(path = "/rest-client")
public class RestClientController {
    RestTemplate restTemplate;
    ClientService clientService;
    @Value("${server.port}")
    String port;

    @Autowired
    public RestClientController(RestTemplate restTemplate, ClientService clientService) {
        this.restTemplate = restTemplate;
        this.clientService = clientService;
    }

    /**
       http://localhost:9090/rest-client/get_clients_for_object
     * @param model
     * @return rest-clients page
     */
    @GetMapping(path = "/get_clients_for_object")
    public String getClientsByRestClientForObject(Model model) {
        Client[] clientsArray = getClientsForObject();
        model.addAttribute("clients", Arrays.asList(clientsArray));
        model.addAttribute("pageTitle", "Получить клиентов из объекта");
        return "rest-clients";
    }

    private Client[] getClientsForObject() {
        Client[] clientsArray;
        if (port.equals("8443")) {
            clientsArray = restTemplate.getForObject("https://localhost:8443/rest-api-clients?recent", Client[].class);
        } else {
            clientsArray = restTemplate.getForObject("http://localhost:9090/rest-api-clients?recent", Client[].class);
        }
        return clientsArray;
    }

    @GetMapping(path = "/get_clients_for_entity")
    public String getClientsByRestClientForEntity(Model model) {
        ResponseEntity<Client[]> clientsArray = getClientsResponseEntity();
        log.warn("getClientsByRestClientForEntity clientsArray.getStatusCode() {}", clientsArray.getStatusCode());
        log.warn("getClientsByRestClientForEntity clientsArray.getStatusCodeValue() {}", clientsArray.getStatusCodeValue());
        log.warn("getClientsByRestClientForEntity clientsArray.getHeaders() {}", clientsArray.getHeaders());
        log.warn("getClientsByRestClientForEntity clientsArray.getHeaders().getContentLanguage() {}", clientsArray.getHeaders().getContentLanguage());
        log.warn("getClientsByRestClientForEntity clientsArray.getHeaders().getLocation() {}", clientsArray.getHeaders().getLocation());
        log.warn("getClientsByRestClientForEntity clientsArray.getHeaders().getAcceptLanguageAsLocales() {}", clientsArray.getHeaders().getAcceptLanguageAsLocales());
        log.warn("getClientsByRestClientForEntity clientsArray.getHeaders().getDate() {}", clientsArray.getHeaders().getDate());
        log.warn("getClientsByRestClientForEntity clientsArray.hasBody() {}", clientsArray.hasBody());
        model.addAttribute("clients", Arrays.asList(clientsArray.getBody()));
        model.addAttribute("pageTitle", "Получить клиентов для сущности");
        return "rest-clients";
    }

    private ResponseEntity<Client[]> getClientsResponseEntity() {
        ResponseEntity<Client[]> clientsArray;
        if (port.equals("8443")) {
            clientsArray = restTemplate.getForEntity("https://localhost:8443/rest-api-clients?recent", Client[].class);
        } else {
            clientsArray = restTemplate.getForEntity("http://localhost:9090/rest-api-clients?recent", Client[].class);
        }
        return clientsArray;
    }

    @GetMapping(path = "/put/{clientId}")
    public String getPutClientForm(Model model,
                                   @PathVariable("clientId") Long clientId) {
        Client client = clientService.findById(clientId).get();
        model.addAttribute("client", client);
        model.addAttribute("pageTitle", "Изменение клиента");
        return "put-clients-form";
    }

    @PostMapping(path = "/put_clients")
    public String putClientByRest(@Valid Client client,
                                  Errors errors,
                                  @ModelAttribute("clientModel") Client clientModel) {
        log.warn("client {}", client);
        putClient(client);
        return "redirect:/rest-client/get_clients_for_object";
    }

    private void putClient(Client client) {
        if (port.equals("8443")) {
            restTemplate.put("https://localhost:8443/rest-api-clients/{clientId}", client, client.getId());
        } else {
            restTemplate.put("http://localhost:9090/rest-api-clients/{clientId}", client, client.getId());
        }
    }

    @GetMapping(path = "/patch/{clientId}")
    public String getPatchClientForm(Model model,
                                     @PathVariable("clientId") Long clientId) {
        if (port.equals("8443")) {
            return "redirect:/rest-client/put/" + clientId;
        }
        Client client = clientService.findById(clientId).get();
        model.addAttribute("client", client);
        model.addAttribute("pageTitle", "Частичное изменение клиента");
        return "patch-clients-form";
    }

    @PostMapping(path = "/patch_clients")
    public String patchClientByRest(Client client,
                                    @ModelAttribute("clientModel") Client clientModel) {
        log.warn("client {}", client);
        patchClient(client);
        return "redirect:/rest-client/get_clients_for_object";
    }


    private void patchClient(Client client) {

        if (port.equals("8443")) {
            restTemplate.patchForObject("https://localhost:8443/rest-api-clients/{clientId}", client, Client.class, client.getId());
        } else {
            RestTemplate restTemplate = new RestTemplate();

            HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
            requestFactory.setConnectTimeout(TIMEOUT);
            requestFactory.setReadTimeout(TIMEOUT);

            restTemplate.setRequestFactory(requestFactory);
            restTemplate.patchForObject("http://localhost:9090/rest-api-clients/{clientId}", client, Client.class, client.getId());
        }
    }

    @GetMapping(path = "/delete/{clientId}")
    public String deleteClientByRest(@PathVariable("clientId") Long clientId) {
        deleteClient(clientId);
        return "redirect:/rest-client/get_clients_for_object";
    }

    private void deleteClient(Long clientId) {
        if (port.equals("8443")) {
            restTemplate.delete("https://localhost:8443/rest-api-clients/{clientId}", clientId);
        } else {
            restTemplate.delete("http://localhost:9090/rest-api-clients/{clientId}", clientId);
        }
    }


    @GetMapping(path = "/post_clients_form")
    public String getPostClientsForm(Model model) {
        model.addAttribute("client", new Client());
        model.addAttribute("pageTitle", "Создание клиента");
        return "post-clients-form";
    }

    @PostMapping(path = "/post_clients_for_object")
    public String postClientsByRestClientForObject(@Valid Client client,
                                                   Errors errors,
                                                   @ModelAttribute("clientModel") Client clientModel) {
        Client postForObjectClient = new Client();
        postForObjectClient = getPostForObjectClient(client, postForObjectClient);
        clientModel.setId(postForObjectClient.getId());
        clientModel.setName(postForObjectClient.getName());
        clientModel.setSurname(postForObjectClient.getSurname());
        clientModel.setEmail(postForObjectClient.getEmail());
        return "redirect:/rest-client/get_clients_for_object";
//        return "redirect:/rest-client/get_post_clients_for_object";
    }

    @GetMapping(path = "/get_post_clients_for_object")
    public String getPostClientsForObject(Model model, @ModelAttribute("clientModel") Client clientModel) {
        model.addAttribute("clients", Arrays.asList(clientModel));
        model.addAttribute("pageTitle", "Показать клиента после создания");
        return "rest-clients";
    }

    private Client getPostForObjectClient(Client client, Client postForObjectClient) {
        log.warn("port {}", port);
        if (port.equals("9090")) {
            postForObjectClient = restTemplate.postForObject("http://localhost:9090/rest-api-clients", client, Client.class);
        } else if (port.equals("8443")) {
            postForObjectClient = restTemplate.postForObject("https://localhost:8443/rest-api-clients", client, Client.class);
        }
        return postForObjectClient;
    }

    @ModelAttribute("clientModel")
    public Client client() {
        return new Client();
    }

}

