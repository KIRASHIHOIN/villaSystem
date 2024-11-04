package com.coding.villaSystem.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
@Data
public class Actor {
    private Integer actorId;
    private String actorName;
    private String actorDescribe;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime actorAddtime;
}
