package com.coding.villaSystem.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class Permission {

    private Integer permissionId;
    private String permissionName;
    private String permissionDescribe;
    private String permissionPath;
    private Integer permissionFatherId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime permissionAddtime;
    private List<Permission> childPermission;
    private Permission fatherPermission;
    private boolean checkStatic;
}
