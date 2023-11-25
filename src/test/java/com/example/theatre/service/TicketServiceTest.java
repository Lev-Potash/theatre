package com.example.theatre.service;

import com.example.theatre.entity.*;
import com.example.theatre.repository.*;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;

@SpringBootTest
@AutoConfigureMockMvc
public class TicketServiceTest {

    @Mock
//    @Autowired
    private TicketRepository ticketRepository;

    @InjectMocks
    private TicketServiceImpl ticketService;

    //    @InjectMocks
    @Autowired
    private ClientServiceImpl clientService;

    @Autowired
    private PerformanceRepository performanceRepository;

    @Autowired
    private TheatreRepository theatreRepository;

    @Autowired
    private PlaceRepository placeRepository;


//    @Test
//    public void returnTicketsByClientIdTest() {
//
//        Client client = clientService.findById(1L).get();
////        Client client = new Client("Alex", "Frolov", "lex@ru.ru");
////        client.setId(1L);
//
//
////        given(ticketService.ticketsByClient(client).get(0)
////                /*ticketRepository.getTicketsByClient(client).get(0)*/) // any() - получить любое значение
////                .willReturn(new Ticket(new Client("Alex", "Frolov", "lex@ru.ru"),
////                        new Schedule(
////                                new TheatrePerformance(performanceRepository.findAll().get(0), theatreRepository.findAll().get(0)),
////                                new Date(new GregorianCalendar(2022, 6, 1).getTime().getTime()), "18:00"),
////                        new PlaceTheatre(placeRepository.findAll().get(0), theatreRepository.findAll().get(0)),
////                        350));
//        Mockito.when(ticketRepository.getTicketsByClient_Id(1L).get(0)).thenReturn(new Ticket(new Client("Alex", "Frolov", "lex@ru.ru"),
//                        new Schedule(
//                                new TheatrePerformance(performanceRepository.findAll().get(0), theatreRepository.findAll().get(0)),
//                                new Date(new GregorianCalendar(2022, 6, 1).getTime().getTime()), "18:00"),
//                        new PlaceTheatre(placeRepository.findAll().get(0), theatreRepository.findAll().get(0)),
//                        350));
//
//        assertEquals(ticketRepository.getTicketsByClient_Id(1L).get(0).getClient().getName(), "Alex");
//
//
//    }

    public List<Client> getClients() {
        List<Client> clients = new ArrayList<>();

        Client client1 = new Client("Alex", "Frolov", "lex@ru.ru");
        Client client2 = new Client("Rex", "Stepanov", "rex@ru.ru");
        Client client3 = new Client("Max", "Gorbunov", "gorb@ru.ru");
        Client client4 = new Client("Oleg", "Gorin", "ol@ru.ru");
        return List.of(client1, client2, client3, client4);
    }
}
