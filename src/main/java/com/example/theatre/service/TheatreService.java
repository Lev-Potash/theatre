package com.example.theatre.service;

import com.example.theatre.entity.Theatre;
import com.example.theatre.repository.TheatreRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("theatreService")
public interface TheatreService {
    public List<Theatre> getTheatres();
}
