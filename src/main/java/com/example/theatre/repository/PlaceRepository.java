package com.example.theatre.repository;

import com.example.theatre.entity.Place;
import com.example.theatre.entity.PlaceTheatre;
import com.example.theatre.entity.Schedule;
import com.example.theatre.entity.TheatrePerformance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


@Repository
public interface PlaceRepository extends JpaRepository<Place, Long>,
        PagingAndSortingRepository<Place, Long>, JpaSpecificationExecutor<Place> {
    @Query("select p.id, p.row, p.place from Place p " +
            "left join  PlaceTheatre pt " +
            "left join  Ticket t " +
            "where t.client.name = ':name' and t.client.surname = ':surname'")
    List<Place> findPlaceByClientNameAndSurname(@Param("name") String name, @Param("surname") String surname);

    // все занятые места за текущубю дату
    @Query("select p.id, p.row, p.place from Place p " +
            "join  PlaceTheatre pt " +
            "join  Schedule sch " +
            "join  Seating s " +
            "join  Theatre t " +
            "where s.isOccupied = true " +
            "and sch.performanceDate = current_date " +
            "and t.theatreName = ':theatreName'")
    List<Place> getIsOccupiedPlacesByTheatreNameAndCurrentDate(@Param("theatreName") String theatreName);

    // все места в театре
//    @Query("select p.id, p.row, p.place from Place p " +
//            "join fetch PlaceTheatre pt " +
//            "join fetch Schedule sch " +
//            "join fetch Seating s " +
//            "join fetch Theatre t " +
//            "where t.theatreName = :theatreName")

//    @Query("select p.id, p.row, p.place from Place p " +
//            "join  PlaceTheatre pt " +
//            "join  Schedule sch " +
//            "join  Seating s " +
//            "join  Theatre t " +
//            "where t.theatreName = :theatreName")

    @Query("select new Place(p.id, p.row, p.place, s.isOccupied) from Place p " +
            "join  p.placeTheatreList pt " +
            "join  pt.seatings s " +
            "join  s.schedule sch " +
            "join  pt.theatre t " +
            "where sch.theatrePerformance = :theatrePerformance and s.schedule = :schedule")
    List<Place> getAllPlacesByTheatrePerformanceAndSchedule(
            @Param("theatrePerformance") TheatrePerformance theatrePerformance,
            @Param("schedule") Schedule schedule
    );

    // все занятые места в театре за указанную дату

//    @Query("select p.id, p.row, p.place from Place p " +
//            "join fetch PlaceTheatre pt " +
//            "join fetch Schedule sch " +
//            "join fetch Seating s " +
//            "join fetch Theatre t " +
//            "where s.isOccupied = true " +
//            "and sch.performanceDate = :performanceDate " +
//            "and t.theatreName = :theatreName")

//    @Query(value = "select p.id, p.row, p.place from Place p " +
//            "join p.placeTheatreList pt " +
//            "join pt.seatings s " +
//            "join s.schedule sch " +
//            "join pt.theatre t " +
//            "where s.isOccupied = true " +
//            "and sch.performanceDate = :performanceDate " +
//            "and t.theatreName = :theatreName", nativeQuery = true)

        @Query(value = "select new Place(p.id, p.row, p.place) from Place p " +
            "join p.placeTheatreList pt " +
            "join pt.seatings s " +
            "join s.schedule sch " +
            "join pt.theatre t " +
            "where s.isOccupied = true " +
            "and sch.performanceDate = :performanceDate " +
            "and t.theatreName = :theatreName")
    List<Place> getIsOccupiedPlacesByPerformanceDateAndTheatreName(
            @Param("performanceDate") Date performanceDate,
            @Param("theatreName") String theatreName
    );
}
