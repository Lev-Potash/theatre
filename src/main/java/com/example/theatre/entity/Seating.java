package com.example.theatre.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@NoArgsConstructor
public class Seating {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;
    @ManyToOne
    @JoinColumn(name = "place_theatre_id")
    private PlaceTheatre placeTheatre;

    @NotNull
    @Column(name = "isoccupied")
    private boolean isOccupied;


    public Seating(Schedule schedule, PlaceTheatre placeTheatre, boolean isOccupied) {
        this.schedule = schedule;
        this.placeTheatre = placeTheatre;
        this.isOccupied = isOccupied;
    }



}
