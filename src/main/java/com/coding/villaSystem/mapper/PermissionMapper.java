package com.coding.villaSystem.mapper;

import com.coding.villaSystem.entity.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PermissionMapper {
    List<Permission> selectPermissionByActorId(Integer actorId);

    List<Permission> selectPermissionByCondition(@Param("condition") String condition, @Param("permissionFatherId") Integer fatherPermissionId);

    List<Permission> selectFatherPermission();

    List<Permission> selectAllPermission();

    boolean insertPermission(Permission permission);

    boolean updatePermission(Permission permission);

    Permission selectPermissionById(Integer permissionId);

    boolean deletePermissionById(Integer permissionId);
}
