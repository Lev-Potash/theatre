package com.example.theatre.service;

import com.example.theatre.entity.Schedule;
import com.example.theatre.entity.Ticket;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TicketService {

    Ticket save(Ticket ticket);
    List<Ticket> ticketsByClientId(Long id);

    List<Ticket> getTicketBySchedule(Schedule schedule);

    List<Ticket> getAllTickets();
}
