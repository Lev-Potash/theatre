package com.example.theatre.repository;

import com.example.theatre.entity.Schedule;
import com.example.theatre.entity.Seating;
import com.example.theatre.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long>,
        PagingAndSortingRepository<Ticket, Long>, JpaSpecificationExecutor<Ticket> {
    List<Ticket> getTicketsByClient_Id(Long id);


    List<Ticket> getTicketBySchedule(Schedule schedule);
}
