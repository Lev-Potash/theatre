package com.example.theatre.configuration;

import com.example.theatre.entity.*;
import com.example.theatre.repository.*;
import com.example.theatre.service.ClientService;
import com.example.theatre.service.PlaceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

@Slf4j
@Configuration
@Profile("!prod")
public class LoaderConfig {

    @Autowired
    ClientService clientService;

    @Autowired
    PlaceService placeService;

//    @Autowired
//    PasswordEncoder encoder;


    @Bean
    public ApplicationRunner dataLoader(TheatreRepository theatreRepository,
                                        PerformanceRepository performanceRepository, PlaceRepository placeRepository,
                                        ClientRepository clientRepository, SeatingRepository seatingRepository,
                                        PlaceTheatreRepository placeTheatreRepository,
                                        ScheduleRepository scheduleRepository,
                                        TheatrePerformanceRepository theatrePerformanceRepository,
                                        TicketRepository ticketRepository, UserRepository userRepository, PasswordEncoder encoder) {
        return args -> {
            userRepository.deleteAll();
            theatreRepository.deleteAll();
            performanceRepository.deleteAll();
            placeRepository.deleteAll();
            clientRepository.deleteAll();
            seatingRepository.deleteAll();
            placeTheatreRepository.deleteAll();
            scheduleRepository.deleteAll();
            theatrePerformanceRepository.deleteAll();
            ticketRepository.deleteAll();
            log.info(theatreRepository.findAll().toString());
            log.info(performanceRepository.findAll().toString());
            log.info(placeRepository.findAll().toString());
            log.info(clientRepository.findAll().toString());
            log.info(seatingRepository.findAll().toString());
            log.info(placeTheatreRepository.findAll().toString());
            log.info(scheduleRepository.findAll().toString());
            log.info(theatrePerformanceRepository.findAll().toString());
            log.info(ticketRepository.findAll().toString());

            userRepository.save(new User("admin", encoder.encode("password"), "admin", "0000"));
            log.info("LOAD USER: {}", userRepository.findAll());

            List<Client> clients = new ArrayList<>();
            clients.add(clientRepository.save(new Client("Alex", "Frolov", "lex@ru.ru")));
            clients.add(clientRepository.save(new Client("Rex", "Stepanov", "rex@ru.ru")));
            clients.add(clientRepository.save(new Client("Max", "Gorbunov", "gorb@ru.ru")));
            clients.add(clientRepository.save(new Client("Oleg", "Gorin", "ol@ru.ru")));
            log.info("Loader Class.");
            log.info("Loader Clients: {}", clientRepository.findAll());
            List<Theatre> theatres = new ArrayList<>();
            theatres.add(theatreRepository.save(new Theatre("Вестник")));
            theatres.add(theatreRepository.save(new Theatre("Сатира")));
            theatres.add(theatreRepository.save(new Theatre("Малый театр")));
            theatres.add(theatreRepository.save(new Theatre("Большой театр")));
            log.info("Loader Theatres: {}", theatreRepository.findAll());
            List<Performance> performances = new ArrayList<>();
            performances.add(performanceRepository.save(new Performance("Горе от ума")));
            performances.add(performanceRepository.save(new Performance("Гамлет")));
            performances.add(performanceRepository.save(new Performance("Вишневый сад")));
            performances.add(performanceRepository.save(new Performance("Не все коту масленница")));
            performances.add(performanceRepository.save(new Performance("Ромео и Джулиета")));
            performances.add(performanceRepository.save(new Performance("Большой перполох")));
            log.info("Loader Performances: {}", performanceRepository.findAll());
            List<Place> placeList = new ArrayList<>();
            placeList.add(placeRepository.save(new Place(1, 1)));
            placeList.add(placeRepository.save(new Place(1, 2)));
            placeList.add(placeRepository.save(new Place(1, 3)));
            placeList.add(placeRepository.save(new Place(1, 4)));
            placeList.add(placeRepository.save(new Place(1, 5)));
            placeList.add(placeRepository.save(new Place(2, 1)));
            placeList.add(placeRepository.save(new Place(2, 2)));
            placeList.add(placeRepository.save(new Place(2, 3)));
            placeList.add(placeRepository.save(new Place(2, 4)));
            placeList.add(placeRepository.save(new Place(2, 5)));
            placeList.add(placeRepository.save(new Place(3, 1)));
            placeList.add(placeRepository.save(new Place(3, 2)));
            placeList.add(placeRepository.save(new Place(3, 3)));
            placeList.add(placeRepository.save(new Place(3, 4)));
            placeList.add(placeRepository.save(new Place(3, 5)));
            log.info("Loader Performances: {}", placeRepository.findAll());
            List<PlaceTheatre> placeTheatres = new ArrayList<>();
            for (Theatre theatre : theatres) {
                for (Place place : placeList) {
                    placeTheatres.add(placeTheatreRepository.save(new PlaceTheatre(place, theatre)));
                }
            }
            log.info("Loader PlaceTheatres: {}", placeTheatreRepository.findAll());
            List<TheatrePerformance> theatrePerformances = new ArrayList<>();
            for (Theatre theatre : theatres) {
                for (Performance performance : performances) {
                    theatrePerformances.add(
                            theatrePerformanceRepository.save(new TheatrePerformance(performance, theatre))
                    );
                }
            }
            log.info("Loader TheatrePerformances: {}", theatrePerformanceRepository.findAll());
            List<Schedule> schedules = new ArrayList<>();
            schedules.add(scheduleRepository.save(new Schedule(
                    theatrePerformances.get(0), new Date(new GregorianCalendar(2022, 6, 1).getTime().getTime()))));
            schedules.add(scheduleRepository.save(new Schedule(
                    theatrePerformances.get(1), new Date(new GregorianCalendar(2022, 6, 3).getTime().getTime()))));
            schedules.add(scheduleRepository.save(new Schedule(
                    theatrePerformances.get(1), new Date(new GregorianCalendar(2023, 5, 10).getTime().getTime()))));
            schedules.add(scheduleRepository.save(new Schedule(
                    theatrePerformances.get(0), new Date(new GregorianCalendar(2023, 5, 10).getTime().getTime()))));
            schedules.add(scheduleRepository.save(new Schedule(
                    theatrePerformances.get(1), new Date(new GregorianCalendar(2023, 5, 29).getTime().getTime()))));
            log.info("Loader Schedules: {}", scheduleRepository.findAll());
            List<Seating> seatings = new ArrayList<>();
            seatings.add(seatingRepository.save(new Seating( schedules.get(0), placeTheatres.get(0), false)));
            seatings.add(seatingRepository.save(new Seating( schedules.get(0), placeTheatres.get(1), false)));
            seatings.add(seatingRepository.save(new Seating( schedules.get(0), placeTheatres.get(2), false)));
            seatings.add(seatingRepository.save(new Seating( schedules.get(0), placeTheatres.get(3), true)));
            seatings.add(seatingRepository.save(new Seating( schedules.get(0), placeTheatres.get(4), false)));
            seatings.add(seatingRepository.save(new Seating( schedules.get(0), placeTheatres.get(5), false)));
            seatings.add(seatingRepository.save(new Seating( schedules.get(0), placeTheatres.get(6), false)));
            seatings.add(seatingRepository.save(new Seating( schedules.get(0), placeTheatres.get(7), false)));
            seatings.add(seatingRepository.save(new Seating( schedules.get(0), placeTheatres.get(8), true)));
            seatings.add(seatingRepository.save(new Seating( schedules.get(0), placeTheatres.get(9), true)));
            seatings.add(seatingRepository.save(new Seating( schedules.get(0), placeTheatres.get(10), false)));
            seatings.add(seatingRepository.save(new Seating( schedules.get(0), placeTheatres.get(11), false)));
            seatings.add(seatingRepository.save(new Seating( schedules.get(0), placeTheatres.get(12), false)));
            seatings.add(seatingRepository.save(new Seating( schedules.get(0), placeTheatres.get(13), false)));
            seatings.add(seatingRepository.save(new Seating( schedules.get(0), placeTheatres.get(14), false)));
            seatings.add(seatingRepository.save(new Seating( schedules.get(1), placeTheatres.get(0), false)));
            seatings.add(seatingRepository.save(new Seating( schedules.get(1), placeTheatres.get(1), false)));
            seatings.add(seatingRepository.save(new Seating( schedules.get(1), placeTheatres.get(2), false)));
            seatings.add(seatingRepository.save(new Seating( schedules.get(1), placeTheatres.get(3), true)));
            seatings.add(seatingRepository.save(new Seating( schedules.get(1), placeTheatres.get(4), false)));
            seatings.add(seatingRepository.save(new Seating( schedules.get(1), placeTheatres.get(5), false)));
            seatings.add(seatingRepository.save(new Seating( schedules.get(1), placeTheatres.get(6), false)));
            seatings.add(seatingRepository.save(new Seating( schedules.get(1), placeTheatres.get(7), false)));
            seatings.add(seatingRepository.save(new Seating( schedules.get(1), placeTheatres.get(8), true)));
            seatings.add(seatingRepository.save(new Seating( schedules.get(1), placeTheatres.get(9), true)));
            seatings.add(seatingRepository.save(new Seating( schedules.get(1), placeTheatres.get(10), false)));
            seatings.add(seatingRepository.save(new Seating( schedules.get(1), placeTheatres.get(11), false)));
            seatings.add(seatingRepository.save(new Seating( schedules.get(1), placeTheatres.get(12), false)));
            seatings.add(seatingRepository.save(new Seating( schedules.get(1), placeTheatres.get(13), false)));
            seatings.add(seatingRepository.save(new Seating( schedules.get(1), placeTheatres.get(14), false)));
            seatings.add(seatingRepository.save(new Seating( schedules.get(2), placeTheatres.get(0), false)));
            seatings.add(seatingRepository.save(new Seating( schedules.get(2), placeTheatres.get(1), false)));
            seatings.add(seatingRepository.save(new Seating( schedules.get(2), placeTheatres.get(2), false)));
            seatings.add(seatingRepository.save(new Seating( schedules.get(2), placeTheatres.get(3), true)));
            seatings.add(seatingRepository.save(new Seating( schedules.get(2), placeTheatres.get(4), false)));
            seatings.add(seatingRepository.save(new Seating( schedules.get(2), placeTheatres.get(5), false)));
            seatings.add(seatingRepository.save(new Seating( schedules.get(2), placeTheatres.get(6), false)));
            seatings.add(seatingRepository.save(new Seating( schedules.get(2), placeTheatres.get(7), false)));
            seatings.add(seatingRepository.save(new Seating( schedules.get(2), placeTheatres.get(8), true)));
            seatings.add(seatingRepository.save(new Seating( schedules.get(2), placeTheatres.get(9), true)));
            seatings.add(seatingRepository.save(new Seating( schedules.get(2), placeTheatres.get(10), false)));
            seatings.add(seatingRepository.save(new Seating( schedules.get(2), placeTheatres.get(11), false)));
            seatings.add(seatingRepository.save(new Seating( schedules.get(2), placeTheatres.get(12), false)));
            seatings.add(seatingRepository.save(new Seating( schedules.get(2), placeTheatres.get(13), false)));
            seatings.add(seatingRepository.save(new Seating( schedules.get(2), placeTheatres.get(14), false)));
            seatings.add(seatingRepository.save(new Seating( schedules.get(3), placeTheatres.get(0), false)));
            seatings.add(seatingRepository.save(new Seating( schedules.get(3), placeTheatres.get(1), false)));
            seatings.add(seatingRepository.save(new Seating( schedules.get(3), placeTheatres.get(2), false)));
            seatings.add(seatingRepository.save(new Seating( schedules.get(3), placeTheatres.get(3), true)));
            seatings.add(seatingRepository.save(new Seating( schedules.get(3), placeTheatres.get(4), false)));
            seatings.add(seatingRepository.save(new Seating( schedules.get(3), placeTheatres.get(5), false)));
            seatings.add(seatingRepository.save(new Seating( schedules.get(3), placeTheatres.get(6), false)));
            seatings.add(seatingRepository.save(new Seating( schedules.get(3), placeTheatres.get(7), false)));
            seatings.add(seatingRepository.save(new Seating( schedules.get(3), placeTheatres.get(8), true)));
            seatings.add(seatingRepository.save(new Seating( schedules.get(3), placeTheatres.get(9), true)));
            seatings.add(seatingRepository.save(new Seating( schedules.get(3), placeTheatres.get(10), true)));
            seatings.add(seatingRepository.save(new Seating( schedules.get(3), placeTheatres.get(11), false)));
            seatings.add(seatingRepository.save(new Seating( schedules.get(3), placeTheatres.get(12), false)));
            seatings.add(seatingRepository.save(new Seating( schedules.get(3), placeTheatres.get(13), false)));
            seatings.add(seatingRepository.save(new Seating( schedules.get(3), placeTheatres.get(14), false)));
            seatings.add(seatingRepository.save(new Seating( schedules.get(4), placeTheatres.get(0), false)));
            seatings.add(seatingRepository.save(new Seating( schedules.get(4), placeTheatres.get(1), false)));
            seatings.add(seatingRepository.save(new Seating( schedules.get(4), placeTheatres.get(2), false)));
            seatings.add(seatingRepository.save(new Seating( schedules.get(4), placeTheatres.get(3), true)));
            seatings.add(seatingRepository.save(new Seating( schedules.get(4), placeTheatres.get(4), false)));
            seatings.add(seatingRepository.save(new Seating( schedules.get(4), placeTheatres.get(5), false)));
            seatings.add(seatingRepository.save(new Seating( schedules.get(4), placeTheatres.get(6), false)));
            seatings.add(seatingRepository.save(new Seating( schedules.get(4), placeTheatres.get(7), false)));
            seatings.add(seatingRepository.save(new Seating( schedules.get(4), placeTheatres.get(8), true)));
            seatings.add(seatingRepository.save(new Seating( schedules.get(4), placeTheatres.get(9), true)));
            seatings.add(seatingRepository.save(new Seating( schedules.get(4), placeTheatres.get(10), false)));
            seatings.add(seatingRepository.save(new Seating( schedules.get(4), placeTheatres.get(11), false)));
            seatings.add(seatingRepository.save(new Seating( schedules.get(4), placeTheatres.get(12), false)));
            seatings.add(seatingRepository.save(new Seating( schedules.get(4), placeTheatres.get(13), false)));
            seatings.add(seatingRepository.save(new Seating( schedules.get(4), placeTheatres.get(14), false)));
            log.info("Loader Seatings: {}", seatingRepository.findAll());
            List<Ticket> tickets = new ArrayList<>();

            tickets.add(
                    ticketRepository.save(new Ticket(clients.get(0), schedules.get(0), placeTheatres.get(0), 350))
            );
            tickets.add(
                    ticketRepository.save(new Ticket(clients.get(1), schedules.get(0), placeTheatres.get(0), 350))
            );
            tickets.add(
                    ticketRepository.save(new Ticket(clients.get(1), schedules.get(1), placeTheatres.get(1), 350))
            );
            tickets.add(
                    ticketRepository.save(new Ticket(clients.get(2), schedules.get(2), placeTheatres.get(2), 350))
            );
            tickets.add(
                    ticketRepository.save(new Ticket(clients.get(3), schedules.get(2), placeTheatres.get(3), 350))
            );


            log.info("Loader Tickets: {}", ticketRepository.findAll());



        };
    }
}
