package com.example.theatre.service;

import com.example.theatre.entity.PlaceTheatre;
import com.example.theatre.entity.Schedule;
import com.example.theatre.entity.Seating;
import org.springframework.stereotype.Service;

@Service
public interface SeatingService {
    Seating getSeatingByPlaceTheatreAndSchedule(PlaceTheatre placeTheatre, Schedule schedule);

    void saveSeating(Seating seating);
}
