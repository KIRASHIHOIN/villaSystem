package com.coding.villaSystem.vo;


import com.coding.villaSystem.entity.Villa;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;
@Data
public class VillaPageVo {
    private List<Villa> pageList;
    private Integer currentPage;
    private Integer totalPage;
    private String condition;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate start;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate end;
    private Integer storeNumber;
}
