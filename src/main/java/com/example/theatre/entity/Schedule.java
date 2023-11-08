package com.example.theatre.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;

    @ManyToOne
    @JoinColumn(name = "theatre_performance_id")
    private TheatrePerformance theatrePerformance;

    @NotNull(message = "Дата должна быть выбрана")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "performance_date")
    private Date performanceDate;

//    @DateTimeFormat(pattern = "HH:mm")
    @Column(name = "performance_time")
    private String performanceTime;

    @ToString.Exclude
    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Ticket> tickets = new ArrayList<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Seating> seatings = new ArrayList<>();

    public Schedule(TheatrePerformance theatrePerformance, Date performanceDate, String performanceTime) {
        this.theatrePerformance = theatrePerformance;
        this.performanceDate = performanceDate;
        this.performanceTime = performanceTime;
    }
}
