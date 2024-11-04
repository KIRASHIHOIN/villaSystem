package com.coding.villaSystem.mapper;

import com.coding.villaSystem.entity.Villa;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class VillaMapperTest {
    @Resource
    VillaMapper villaMapper;

    @Test
    void getVillaWithStore() {
        List<Villa> villaWithStore = villaMapper.getVillaWithStore(null, null, null, null);
        System.out.println(villaWithStore);
    }

    @Test
    void getVillaByVillaId() {
    }

    @Test
    void addVilla() {
    }

    @Test
    void updateVilla() {
    }

    @Test
    void deleteVilla() {
    }
}