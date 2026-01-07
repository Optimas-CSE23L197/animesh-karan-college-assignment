package com.example.assignment_two.model;

import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PriceWindow {
    private LocalTime starTime;
    private LocalTime endTime;
    private int price;
}
