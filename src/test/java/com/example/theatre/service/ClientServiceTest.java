package com.example.theatre.service;

import com.example.theatre.entity.Client;
import com.example.theatre.repository.ClientRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;

@SpringBootTest
@AutoConfigureMockMvc
public class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientServiceImpl clientService;

    @Test
    void testRepositoryFindById() {

        given(clientRepository.findById(1L)) // any() - получить любое значение
                .willReturn(Optional.of(new Client("Alex", "Frolov", "lex@ru.ru")));

        assertEquals(clientRepository.findById(1L).get().getName(), "Alex");
    }

    @Test
    void testRepositoryFindClientByTicketId() {

        given(clientRepository.findClientByTicketId(1L)) // any() - получить любое значение
                .willReturn(Optional.of(new Client("Alex", "Frolov", "lex@ru.ru")));

        assertEquals(clientRepository.findClientByTicketId(1L).get().getName(), "Alex");
    }

    @Test
    public void returnAllClientTest() {

        List<Client> clients = getClients();

        Mockito.when(clientRepository.findAll()).thenReturn(clients);

        List<Client> result = clientService.findAllClients();

        assertNotNull(result);
        assertEquals(4, result.size());
        assertEquals(clients.get(1), result.get(1));

    }

    public List<Client> getClients() {
        List<Client> clients = new ArrayList<>();

        Client client1 = new Client("Alex", "Frolov", "lex@ru.ru");
        Client client2 = new Client("Rex", "Stepanov", "rex@ru.ru");
        Client client3 = new Client("Max", "Gorbunov", "gorb@ru.ru");
        Client client4 = new Client("Oleg", "Gorin", "ol@ru.ru");
        return List.of(client1, client2, client3, client4);
    }
}
