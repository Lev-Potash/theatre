package com.example.theatre.service;

import com.example.theatre.entity.Performance;
import com.example.theatre.entity.Theatre;
import com.example.theatre.entity.TheatrePerformance;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface TheatrePerformanceService {
    TheatrePerformance findTheatrePerformance(Theatre theatre, Performance performance);

    List<TheatrePerformance> getAllTheatrePerformance();
}
