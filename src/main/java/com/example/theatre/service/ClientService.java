package com.example.theatre.service;

import com.example.theatre.entity.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ClientService {

    Optional<Client> findById(Long id);
    Page<Client> findAllClients(Pageable pageable);

    List<Client> findAllClients();
    Optional<Client> findClientByTicket(Long id);
    Client save(Client client);

    void deleteById(Long id);
}
