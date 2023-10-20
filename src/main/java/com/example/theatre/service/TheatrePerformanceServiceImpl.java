package com.example.theatre.service;

import com.example.theatre.entity.Performance;
import com.example.theatre.entity.Theatre;
import com.example.theatre.entity.TheatrePerformance;
import com.example.theatre.repository.TheatrePerformanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TheatrePerformanceServiceImpl implements TheatrePerformanceService {
    TheatrePerformanceRepository theatrePerformanceRepository;

    @Autowired
    public TheatrePerformanceServiceImpl(TheatrePerformanceRepository theatrePerformanceRepository) {
        this.theatrePerformanceRepository = theatrePerformanceRepository;
    }

    @Override
    public TheatrePerformance findTheatrePerformance(Theatre theatre, Performance performance) {
        if (theatrePerformanceRepository.findTheatrePerformanceByTheatreAndPerformance(theatre, performance).isPresent()) {
            return theatrePerformanceRepository.findTheatrePerformanceByTheatreAndPerformance(theatre, performance).get();
        }
        return null;
    }

    @Override
    public List<TheatrePerformance> getAllTheatrePerformance() {
        return theatrePerformanceRepository.findAll();
    }
}
