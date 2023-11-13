package com.example.theatre.service;

import com.example.theatre.entity.Schedule;
import com.example.theatre.entity.TheatrePerformance;
import com.example.theatre.repository.ScheduleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

//@Service("scheduleService")
@Service
@Slf4j
public class ScheduleServiceImpl implements ScheduleService {

    private ScheduleRepository scheduleRepository;

    @Autowired
    public ScheduleServiceImpl(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    @Override
    public Schedule getScheduleByTheatrePerformanceAndPerformanceDate(TheatrePerformance theatrePerformance, Date performanceDate) {
        log.info("theatre_performance_id: {}", theatrePerformance.getId());
        log.info("performance date: {}", performanceDate);
//        if (scheduleRepository.findScheduleByTheatrePerformanceAndPerformanceDate(theatrePerformance, performanceDate).isEmpty()) {
//            return null;
//        }
        return scheduleRepository.findScheduleByTheatrePerformanceAndPerformanceDate(theatrePerformance, performanceDate).get();
    }

    @Override
    public List<Date> getPerformanceDatesByTheatrePerformance(TheatrePerformance theatrePerformance) {
        if (theatrePerformance.getTheatre() == null || theatrePerformance.getPerformance() == null
                || theatrePerformance.getId() == null) {
            log.info("getPerformanceDatesByTheatrePerformance. theatrePerformance is null");
            return null;
        }
        log.info("theatre performance Theatre: {} " ,theatrePerformance.getTheatre());
        log.info("theatre performance Performance: {}", theatrePerformance.getPerformance());
        log.info("theatrePerformanceId: {} " ,theatrePerformance.getId());
        if (scheduleRepository.getScheduleByTheatrePerformance(theatrePerformance).isEmpty()) {
            log.error("Not Found performanceDate Schedule with assigned theatrePerformance.");
            return null;
        }
        Set<Date> performanceDates = new HashSet<>();
        for (Schedule schedule:
        scheduleRepository.getScheduleByTheatrePerformance(theatrePerformance)) {
            performanceDates.add(schedule.getPerformanceDate());
        }
            return performanceDates.stream().sorted().collect(Collectors.toList());
    }


    @Override
    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }
}
