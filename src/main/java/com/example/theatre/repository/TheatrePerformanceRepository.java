package com.example.theatre.repository;

import com.example.theatre.entity.Performance;
import com.example.theatre.entity.Theatre;
import com.example.theatre.entity.TheatrePerformance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TheatrePerformanceRepository extends JpaRepository<TheatrePerformance, Long>,
        PagingAndSortingRepository<TheatrePerformance, Long>, JpaSpecificationExecutor<TheatrePerformance> {

    Optional<TheatrePerformance> findTheatrePerformanceByTheatreAndPerformance(Theatre theatre, Performance performance);
}
