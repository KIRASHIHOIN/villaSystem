package com.coding.villaSystem.mapper;

import com.coding.villaSystem.entity.Store;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StoreMapperTest {

    @Resource
    private StoreMapper storeMapper;
    @Test
    void getStoreWithCity() {
        List<Store> storeWithCity = storeMapper.getStoreWithCity(null, null, null, null);
        System.out.println(storeWithCity);
    }

    @Test
    void getStoreByStoreId() {
        Store store = storeMapper.getStoreByStoreId(1);
        System.out.println(store);
    }

    @Test
    void addStore() {
    }

    @Test
    void updateStore() {
    }
}