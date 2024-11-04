package com.coding.villaSystem.service;

import com.coding.villaSystem.entity.Permission;
import com.coding.villaSystem.vo.ActorPageVo;
import com.coding.villaSystem.vo.PermissionPageVo;

import java.time.LocalDate;
import java.util.List;

public interface PermissionService {
    List<Permission> selectPermissionByActorId(Integer actorId);
    PermissionPageVo selectPermissionByPage(String condition, Integer fatherPermissionId, Integer currentPage);

    List<Permission> selectFatherPermission();

    List<Permission> selectAllPermissionByActorId(Integer actorId);

    boolean updatePermissionWithActor(Integer actorId,Integer[] permissionIds);
    List<Permission> selectAllPermission();

    boolean insertPermission(Permission permission);

    boolean updatePermission(Permission permission);

    Permission selectPermissionById(Integer permissionId);

    boolean deletePermission(Integer permissionId);
    boolean deleteCheckedPermission(Integer[] permissionIds);
}
