package com.coding.villaSystem.mapper;

import com.coding.villaSystem.entity.Store;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface StoreMapper {

    List<Store> getStoreWithCity(@Param("condition") String condition,
                                 @Param("start") LocalDate start,
                                 @Param("end") LocalDate end,
                                 @Param("cityNumber") Integer cityNumber);

    Store getStoreByStoreId(Integer storeId);

    List<Store> getAllStore();

    boolean addStore(Store store);

    boolean updateStore(Store store);

    boolean deleteStore(Integer storeId);
}
