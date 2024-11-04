package com.coding.villaSystem.vo;

import com.coding.villaSystem.entity.User;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;
@Data
public class UserPageVo {
    private List<User> pageList;
    private Integer currentPage;
    private Integer totalPage;
    private String condition;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate start;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate end;
}
