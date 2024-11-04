package com.coding.villaSystem.service;

import com.coding.villaSystem.entity.Store;
import com.coding.villaSystem.vo.StorePageVo;

import java.time.LocalDate;
import java.util.List;

public interface StoreService {

    StorePageVo getStorePageVo(String condition, LocalDate start, LocalDate end, Integer currentPage, Integer cityNumber);
    Store getStoreById(Integer storeId);
    boolean addStore(Store store);
    boolean updateStore(Store store);
    List<Store> getAllStore();
    boolean deleteStore(Integer storeId);
    boolean deleteCheckedStore(Integer[] storeIds);
}
