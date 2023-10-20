package com.example.theatre.service;

import com.example.theatre.entity.Schedule;
import com.example.theatre.entity.Ticket;
import com.example.theatre.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {

    TicketRepository ticketRepository;

    @Autowired
    public TicketServiceImpl(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Override
    public Ticket save(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    @Override
    public List<Ticket> ticketsByClientId(Long id) {
        return ticketRepository.getTicketsByClient_Id(id);
    }

    @Override
    public List<Ticket> getTicketBySchedule(Schedule schedule) {
        return ticketRepository.getTicketBySchedule(schedule);
    }

    @Override
    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }
}
