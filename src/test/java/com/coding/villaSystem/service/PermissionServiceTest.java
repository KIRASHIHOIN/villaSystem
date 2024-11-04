package com.coding.villaSystem.service;

import com.coding.villaSystem.entity.Permission;
import com.coding.villaSystem.mapper.PermissionMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class PermissionServiceTest {

    @Resource
    PermissionMapper permissionMapper;
    @Test
    void selectPermissionByActorId() {
    }

    @Test
    void selectPermissionByPage() {
        String condition = "管理";
        Integer fatherPermissionId = 1;
        List<Permission> permissions = permissionMapper.selectPermissionByCondition(condition,fatherPermissionId);
        System.out.println(permissions);
    }

    @Test
    void selectFatherPermission() {
    }
}