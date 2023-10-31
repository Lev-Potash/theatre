package com.example.theatre.service;

import com.example.theatre.entity.Schedule;
import com.example.theatre.entity.TheatrePerformance;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public interface ScheduleService {
    Schedule getScheduleByTheatrePerformanceAndPerformanceDate(TheatrePerformance theatrePerformance,
                                                               Date performanceDate);
    List<Date> getPerformanceDatesByTheatrePerformance(TheatrePerformance theatrePerformance);


    List<Schedule> getAllSchedules();
}
