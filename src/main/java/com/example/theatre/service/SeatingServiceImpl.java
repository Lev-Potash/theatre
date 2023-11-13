package com.example.theatre.service;

import com.example.theatre.entity.PlaceTheatre;
import com.example.theatre.entity.Schedule;
import com.example.theatre.entity.Seating;
import com.example.theatre.repository.SeatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("seatingService")
public class SeatingServiceImpl implements SeatingService {
    SeatingRepository seatingRepository;

    @Autowired
    public SeatingServiceImpl(SeatingRepository seatingRepository) {
        this.seatingRepository = seatingRepository;
    }

    @Override
    public Seating getSeatingByPlaceTheatreAndSchedule(PlaceTheatre placeTheatre, Schedule schedule) {
//        if (seatingRepository.findSeatingByPlaceTheatreAndSchedule(placeTheatre, schedule).isEmpty()) {
//            return null;
//        }
        return seatingRepository.findSeatingByPlaceTheatreAndSchedule(placeTheatre, schedule).get();
    }

    @Override
    public void saveSeating(Seating seating) {
        seatingRepository.save(seating);
    }
}
