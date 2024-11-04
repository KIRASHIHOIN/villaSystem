package com.coding.villaSystem.mapper;

import com.coding.villaSystem.entity.Actor;
import com.coding.villaSystem.entity.Permission;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PermissionMapperTest {

    @Resource
    PermissionMapper permissionMapper;

    @Test
    void selectPermissionByActorId() {
        Actor actor = new Actor();
        actor.setActorId(1);
        List<Permission> permissions = permissionMapper.selectPermissionByActorId(actor.getActorId());
        System.out.println(permissions);
    }
    @Test
    void selectPermissionByCondition(){
        String condition = "管理";
        Integer fatherPermissionId = 1;
        List<Permission> permissions = permissionMapper.selectPermissionByCondition(condition,fatherPermissionId);
        System.out.println(permissions);
    }
    @Test
    void updatePermission(){
        Permission permission = new Permission();
        permission.setPermissionId(11);
        permission.setPermissionName("villa_management");
        permission.setPermissionDescribe("别墅管理");
        permission.setPermissionPath("/villa/goIndex");
        permission.setPermissionFatherId(2);
        permissionMapper.updatePermission(permission);
        System.out.println(permission);
    }
    @Test
    void deletePermission(){
        boolean b = permissionMapper.deletePermissionById(14);
        if (b) {
            System.out.println("成功了");
        }
    }
}