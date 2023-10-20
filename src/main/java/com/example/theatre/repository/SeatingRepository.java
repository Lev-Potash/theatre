package com.example.theatre.repository;

import com.example.theatre.entity.Place;
import com.example.theatre.entity.PlaceTheatre;
import com.example.theatre.entity.Schedule;
import com.example.theatre.entity.Seating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SeatingRepository extends JpaRepository<Seating, Long>,
        PagingAndSortingRepository<Seating, Long>, JpaSpecificationExecutor<Seating> {

    Optional<Seating> findSeatingByPlaceTheatreAndSchedule(PlaceTheatre placeTheatre, Schedule schedule);
}
