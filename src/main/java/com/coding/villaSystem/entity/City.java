package com.coding.villaSystem.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class City {
    private Integer cityId;
    private String cityName;
    private String provinceName;
    private Integer addUserId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate cityAddTime;
    private User user;
}
