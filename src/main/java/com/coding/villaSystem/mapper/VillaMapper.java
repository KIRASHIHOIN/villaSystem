package com.coding.villaSystem.mapper;


import com.coding.villaSystem.entity.Villa;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface VillaMapper {
    List<Villa> getVillaWithStore(@Param("condition") String condition,
                                 @Param("start") LocalDate start,
                                 @Param("end") LocalDate end,
                                 @Param("storeNumber") Integer storeNumber);

    Villa getVillaByVillaId(Integer villaId);

    boolean addVilla(Villa villa);

    boolean updateVilla(Villa villa);

    boolean deleteVilla(Integer villaId);
}
