package com.example.theatre.repository;

import com.example.theatre.entity.Place;
import com.example.theatre.entity.PlaceTheatre;
import com.example.theatre.entity.Theatre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlaceTheatreRepository extends JpaRepository<PlaceTheatre, Long>,
        PagingAndSortingRepository<PlaceTheatre, Long>, JpaSpecificationExecutor<PlaceTheatre> {

    Optional<PlaceTheatre> findPlaceTheatreByPlaceAndTheatre(Place place, Theatre theatre);
}
