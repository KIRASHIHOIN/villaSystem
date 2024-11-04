package com.coding.villaSystem.service.impl;

import com.coding.villaSystem.entity.Permission;
import com.coding.villaSystem.service.PermissionService;
import com.coding.villaSystem.vo.PermissionPageVo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class PermissionServiceImplTest {

    @Resource
    PermissionService permissionService;
    @Test
    void selectPermissionByActorId() {
    }

    @Test
    void selectPermissionByPage() {
        String condition = "管理";
        Integer fatherPermissionId = 1;
        Integer currentPage = 1;
        PermissionPageVo permissionPageVo = permissionService.selectPermissionByPage(condition, fatherPermissionId, currentPage);
        System.out.println(permissionPageVo);
    }

    @Test
    void selectFatherPermission() {
    }
}