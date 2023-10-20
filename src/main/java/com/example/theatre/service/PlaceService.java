package com.example.theatre.service;

import com.example.theatre.entity.Place;
import com.example.theatre.entity.PlaceTheatre;
import com.example.theatre.entity.Schedule;
import com.example.theatre.entity.TheatrePerformance;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public interface PlaceService {

    List<Place> getAllPlaces();
    List<Place> getAllPlacesByTheatrePerformanceAndSchedule(TheatrePerformance theatrePerformance, Schedule schedule);
    List<Place> getOccupiedPlacesByCurrentDate(String theatre);
    List<Place> getOccupiedPlacesByTheatreNameAndPerformanceDate(String theatre, Date performanceDate);

    List<Place> getPlaceByClientNameAndSurname(String name, String surname);
}
