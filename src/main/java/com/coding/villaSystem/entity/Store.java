package com.coding.villaSystem.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class Store {
    private Integer storeId;
    private String storeName;
    private String storeTel;
    private String storeCity;
    private String storeAddress;
    private Integer addUserId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime storeAddTime;
    private Integer storeCityId;
    private City city;
    private User user;
}
