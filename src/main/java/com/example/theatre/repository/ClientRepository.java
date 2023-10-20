package com.example.theatre.repository;

import com.example.theatre.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//select c from boot.Client c
//        join boot.Ticket t on t.client_id=c.id
//        where t.id = 1;
@Repository
public interface ClientRepository extends JpaRepository<Client, Long>,
        PagingAndSortingRepository<Client, Long> {
    @Query("select c from Client c " +
            "join fetch Ticket t " +
            "where t.id = :id")
    Optional<Client> findClientByTicketId(Long id);

    List<Client> findAll();

}
