package com.example.theatre.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "place_theatre")
@NoArgsConstructor
public class PlaceTheatre {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "place_id")
    private Place place;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "theatre_id")
    private Theatre theatre;

    @ToString.Exclude
    @OneToOne(mappedBy = "place_theatre", cascade = CascadeType.ALL)
    private Ticket ticket;

    @ToString.Exclude
    @OneToMany(mappedBy = "placeTheatre", cascade = CascadeType.ALL,
        orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Seating> seatings = new ArrayList<>();

    public PlaceTheatre(Place place, Theatre theatre) {
        this.place = place;
        this.theatre = theatre;
    }
}
