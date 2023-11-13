package com.example.theatre.service;

import com.example.theatre.entity.Theatre;
import com.example.theatre.repository.TheatreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("theatreService")
public class TheatreServiceImpl implements TheatreService {

    TheatreRepository theatreRepository;

    @Autowired
    public TheatreServiceImpl(TheatreRepository theatreRepository) {
        this.theatreRepository = theatreRepository;
    }

    @Override
    public List<Theatre> getTheatres() {
        return theatreRepository.findAll();
    }
}
