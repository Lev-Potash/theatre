package com.example.theatre.repository;

import com.example.theatre.entity.Performance;
import com.example.theatre.entity.Theatre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PerformanceRepository extends JpaRepository<Performance, Long>,
        PagingAndSortingRepository<Performance, Long> {
    List<Performance> findAll();
}
