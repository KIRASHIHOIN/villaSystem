package com.coding.villaSystem.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class User {

    private Integer userId;

    private String userAccount;

    private String userPassword;

    private String userName;

    private Integer actorId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime userAddtime;

    private Actor actor;
}
