package com.example.theatre.service;

import com.example.theatre.entity.Place;
import com.example.theatre.entity.PlaceTheatre;
import com.example.theatre.entity.Schedule;
import com.example.theatre.entity.TheatrePerformance;
import com.example.theatre.repository.PlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PlaceServiceImpl implements PlaceService {
    private PlaceRepository placeRepository;

    @Autowired
    public PlaceServiceImpl(PlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
    }


    @Override
    public List<Place> getAllPlaces() {
        return placeRepository.findAll();
    }

    @PreAuthorize("hasRole('USER')")
    @Override
    public List<Place> getAllPlacesByTheatrePerformanceAndSchedule(TheatrePerformance theatrePerformance, Schedule schedule) {
        return placeRepository.getAllPlacesByTheatrePerformanceAndSchedule(theatrePerformance, schedule);
    }

    @Override
    public List<Place> getOccupiedPlacesByCurrentDate(String theatre) {
        return placeRepository.getIsOccupiedPlacesByTheatreNameAndCurrentDate(theatre);
    }

    @Override
    public List<Place> getOccupiedPlacesByTheatreNameAndPerformanceDate(String theatre, Date performanceDate) {
        return placeRepository.getIsOccupiedPlacesByPerformanceDateAndTheatreName(performanceDate, theatre);
    }

    @Override
    public List<Place> getPlaceByClientNameAndSurname(String name, String surname) {
        return placeRepository.findPlaceByClientNameAndSurname(name, surname);
    }
}
