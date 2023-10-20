package com.example.theatre.service;

import com.example.theatre.entity.Performance;
import com.example.theatre.repository.PerformanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PerformanceService {
    private PerformanceRepository performanceRepository;

    @Autowired
    public PerformanceService(PerformanceRepository performanceRepository) {
        this.performanceRepository = performanceRepository;
    }

   public List<Performance> getPerformanceList() {
        return performanceRepository.findAll();
    }
}
