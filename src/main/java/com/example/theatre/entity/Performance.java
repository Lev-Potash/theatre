package com.example.theatre.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table
@NoArgsConstructor
public class Performance {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Long id;

    @NotNull
    @NotBlank(message = "Поле Спектакль должно быть выбрано")
    @Size(min=1, message="Поле Спектакль должно быть выбрано")
    @Column(name = "performance_name")
    private String performanceName;

    @ToString.Exclude
    @OneToMany(mappedBy = "performance", cascade = CascadeType.ALL,
                orphanRemoval = true)
    List<TheatrePerformance> theatre_performanceList = new ArrayList<>();

    public Performance(String performanceName) {
        this.performanceName = performanceName;
    }
}
