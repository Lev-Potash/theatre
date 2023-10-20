package com.example.theatre.entity;

import lombok.Data;

import java.util.Date;

@Data
public class SimplePerformanceDateAndCost {
    private Date performanceDate;
    private Integer cost;
}
