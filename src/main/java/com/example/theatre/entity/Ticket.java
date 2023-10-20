package com.example.theatre.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@NoArgsConstructor
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;
    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    @OneToOne
    @JoinColumn(name = "place_theatre_id", referencedColumnName = "id")
    private PlaceTheatre place_theatre;

    @NotNull
    private int cost;

    public Ticket(Client client, Schedule schedule, PlaceTheatre place_theatre, int cost) {
        this.client = client;
        this.schedule = schedule;
        this.place_theatre = place_theatre;
        this.cost = cost;
    }
}
