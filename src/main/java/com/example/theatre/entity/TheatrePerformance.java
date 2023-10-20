package com.example.theatre.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
@Data
@Entity
@Table(name = "theatre_performance")
@NoArgsConstructor
public class TheatrePerformance {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "performance_id")
    private Performance performance;


    @NotNull
    @ManyToOne
    @JoinColumn(name = "theatre_id")
    private Theatre theatre;

    @ToString.Exclude
    @OneToMany(mappedBy = "theatrePerformance", cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Schedule> schedules = new ArrayList<>();

    public TheatrePerformance(Performance performance, Theatre theatre) {
        this.performance = performance;
        this.theatre = theatre;
    }
}
