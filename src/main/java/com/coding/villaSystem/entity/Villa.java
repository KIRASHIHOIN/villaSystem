package com.coding.villaSystem.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class Villa {
    private Integer villaId;
    private String villaName;
    private Integer villaStoreId;
    private Integer villaAddUserId;
    private Double villaPrice;
    private String villaImage;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime villaAddTime;
    private Store store;
    private User user;
}
