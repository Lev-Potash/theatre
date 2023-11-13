package com.example.theatre.service;

import com.example.theatre.entity.Client;
import com.example.theatre.repository.ClientRepository;
import com.example.theatre.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("clientService")
public class ClientServiceImpl implements ClientService {

    ClientRepository clientRepository;

    TicketRepository ticketRepository;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository, TicketRepository ticketRepository) {
        this.clientRepository = clientRepository;
        this.ticketRepository = ticketRepository;
    }


    @Override
    public Optional<Client> findById(Long id) {
        return clientRepository.findById(id);
    }

    @Override
    public Page<Client> findAllClients(Pageable pageable) {
        return clientRepository.findAll(pageable);
    }

    @Override
    public List<Client> findAllClients() {
        return clientRepository.findAll();
    }

    @Override
    public Optional<Client> findClientByTicket(Long id) {
        return clientRepository.findClientByTicketId(id);
    }

    @Override
    public Client save(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public void deleteById(Long id) {
        try {
            clientRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {

        }
    }


}
