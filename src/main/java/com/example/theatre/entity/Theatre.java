package com.example.theatre.entity;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@RequiredArgsConstructor
public class Theatre {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Long id;

    @NotNull
    @NotBlank(message = "Поле Театр должно быть выбрано")
    @Size(min=1, message="Поле Театр должно быть выбрано")
    @Column(name = "theatre_name")
    private String theatreName;

    @ToString.Exclude
    @OneToMany(mappedBy = "theatre", cascade = CascadeType.ALL ,
            orphanRemoval = true)
    private List<TheatrePerformance> theatre_performanceList = new ArrayList<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "theatre", cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<PlaceTheatre> placeTheatres = new ArrayList<>();

    public Theatre(String theatreName) {
        this.theatreName = theatreName;
    }

    //    @OneToMany
//    private List<Performance> performanceList = new ArrayList<>();
//    @OneToMany
//    private List<Place> places = new ArrayList<>();
//
//    public void addPerformance(Performance performance) {
//        this.performanceList.add(performance);
//    }
//
//    public void addPlace(Place place) {
//        this.places.add(place);
//    }
}
