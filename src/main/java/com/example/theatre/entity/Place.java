package com.example.theatre.entity;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@RequiredArgsConstructor
public class Place {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    @Column(name = "`row`")
    private int row;
    @NotNull
    @Column(name = "`place`")
    private int place;

    @Transient
    private boolean isOccupied;


    @ToString.Exclude
    @OneToMany(mappedBy = "place", cascade = CascadeType.ALL ,
            orphanRemoval = true)
    public List<PlaceTheatre> placeTheatreList = new ArrayList<>();

    public Place(int row, int place) {
        this.row = row;
        this.place = place;
    }

    public Place(Long id, int row, int place) {
        this.id = id;
        this.row = row;
        this.place = place;
    }



    public Place(Long id, int row, int place, boolean isOccupied) {
        this.id = id;
        this.row = row;
        this.place = place;
        this.isOccupied = isOccupied;
    }
}
