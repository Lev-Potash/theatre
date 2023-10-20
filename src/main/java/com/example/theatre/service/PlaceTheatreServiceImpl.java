package com.example.theatre.service;

import com.example.theatre.entity.Place;
import com.example.theatre.entity.PlaceTheatre;
import com.example.theatre.entity.Theatre;
import com.example.theatre.repository.PlaceTheatreRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class PlaceTheatreServiceImpl implements PlaceTheatreService {
    private PlaceTheatreRepository placeTheatreRepository;

    @Autowired
    public PlaceTheatreServiceImpl(PlaceTheatreRepository placeTheatreRepository) {
        this.placeTheatreRepository = placeTheatreRepository;
    }

    @Override
    public PlaceTheatre getPlaceTheatreByPlaceAndTheatre(Place place, Theatre theatre) {
        if (place == null || theatre == null) {
            log.error("Theatre or place is null");
            return null;
        }
//        if (placeTheatreRepository.findPlaceTheatreByPlaceAndTheatre(place, theatre).isEmpty()) {
//            return null;
//        }
        return placeTheatreRepository.findPlaceTheatreByPlaceAndTheatre(place, theatre).get();
    }

    @Override
    public List<PlaceTheatre> getAllPlaceTheatres() {
        return placeTheatreRepository.findAll();
    }
}
