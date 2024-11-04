package com.coding.villaSystem.service;

import com.coding.villaSystem.entity.Villa;
import com.coding.villaSystem.vo.VillaPageVo;

import java.time.LocalDate;

public interface VillaService {
    VillaPageVo getVillaPageVo(String condition, LocalDate start, LocalDate end, Integer currentPage, Integer storeNumber);
    Villa getVillaById(Integer villaId);
    boolean addVilla(Villa villa);
    boolean updateVilla(Villa villa);
    boolean deleteVilla(Integer villaId);
    boolean deleteCheckedVilla(Integer[] villaIds);
}
