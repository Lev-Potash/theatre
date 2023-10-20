package com.example.theatre.repository;

import com.example.theatre.entity.Schedule;
import com.example.theatre.entity.TheatrePerformance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long>,
        PagingAndSortingRepository<Schedule, Long>, JpaSpecificationExecutor<ScheduleRepository> {

    @Query("select s from Schedule s where s.theatrePerformance = :theatrePerformance " +
            "and s.performanceDate = :date")
    Optional<Schedule> findScheduleByTheatrePerformanceAndPerformanceDate(TheatrePerformance theatrePerformance, @Param(value = "date") Date performanceDate);


//    @Query("select s from Schedule s where s.theatrePerformance = :theatrePerformance")
    List<Schedule> getScheduleByTheatrePerformance(TheatrePerformance theatrePerformance);
}
