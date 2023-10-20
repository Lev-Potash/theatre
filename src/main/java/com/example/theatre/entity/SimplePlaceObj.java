package com.example.theatre.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * Составной объект, который хранит список мест,
 * для того чтобы в thymeleaf записать в поле списка
 * множество значений и достать эти значения из него
 * при Post обработке
 */
@ToString
@NoArgsConstructor
@Setter
@Getter
public class SimplePlaceObj {
    List<Place> placeList;
}
