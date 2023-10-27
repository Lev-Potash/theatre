package com.example.theatre.entity;

import lombok.Data;

import java.util.List;

@Data
public class CommonTheatreReportObject {

    List<Client> clients;
    List<Performance> performances;
    List<Place> places;
//    List<PlaceTheatre> placeTheatres;
    List<Schedule> schedules;
    List<Theatre> theatres;
//    List<TheatrePerformance> theatrePerformances;
    List<Ticket> tickets;




//    private Long id;
//    private String name;
//    private String surname;
//    private String email;
//
//    private String performanceName;
//
//    private int row;
//    private int place;
//
////    @ManyToOne(fetch = FetchType.EAGER)
////    @JoinColumn(name = "place_id")
////    private Place place;
////
////    @ManyToOne(fetch = FetchType.EAGER)
////    @JoinColumn(name = "theatre_id")
////    private Theatre theatre;
////
////    @ToString.Exclude
////    @OneToOne(mappedBy = "place_theatre", cascade = CascadeType.ALL)
////    private Ticket ticket;
//
//    private Date performanceDate;
//    private Integer cost;
}
