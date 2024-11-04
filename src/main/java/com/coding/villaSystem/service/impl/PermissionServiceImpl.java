package com.coding.villaSystem.service.impl;


import com.coding.villaSystem.entity.ActorPermission;
import com.coding.villaSystem.entity.Permission;
import com.coding.villaSystem.mapper.ActorAndPermissionMapper;
import com.coding.villaSystem.mapper.PermissionMapper;
import com.coding.villaSystem.service.PermissionService;

import com.coding.villaSystem.vo.PermissionPageVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Resource
    PermissionMapper permissionMapper;
    @Resource
    ActorAndPermissionMapper actorAndPermissionMapper;

    @Override
    public List<Permission> selectPermissionByActorId(Integer actorId) {
        return permissionMapper.selectPermissionByActorId(actorId);
    }

    @Override
    public PermissionPageVo selectPermissionByPage(String condition, Integer fatherPermissionId, Integer currentPage) {
        // 设置当前页码为1，如果当前页码小于1，则设置当前页码为1
        if (currentPage == null || currentPage < 1) {
            currentPage = 1;
        }
        // 使用PageHelper工具类 beginningPage(currentPage)方法设置起始页码，分页插件会根据传入的参数自动计算分页后的具体页码
        PageHelper.startPage(currentPage, 5);
        // 从数据库中查询出满足条件的权限列表
        List<Permission> permissions = permissionMapper.selectPermissionByCondition(condition, fatherPermissionId);
        // 创建一个PermissionPageVo对象，用于封装查询出的权限列表和分页信息
        PermissionPageVo permissionPageVo = new PermissionPageVo();
        // 创建一个PageInfo对象，用于封装分页后的权限列表
        PageInfo<Permission> pi = new PageInfo<>(permissions);
        if (pi.getPages() == 0) {
            pi.setPages(1);
        }
        // 将查询出的权限列表封装到PermissionPageVo对象中
        permissionPageVo.setPageList(permissions);
        // 将分页后的当前页码封装到PermissionPageVo对象中
        permissionPageVo.setCurrentPage(pi.getPageNum());
        // 将分页后的总页码封装到PermissionPageVo对象中
        permissionPageVo.setTotalPage(pi.getPages());
        // 将查询出的条件封装到PermissionPageVo对象中
        permissionPageVo.setCondition(condition);
        // 将查询出的父权限ID封装到PermissionPageVo对象中
        permissionPageVo.setFatherPermissionId(fatherPermissionId);
        // 返回封装好的PermissionPageVo对象，其中包含了查询出的权限列表和分页信息
        return permissionPageVo;
    }

    @Override
    public List<Permission> selectFatherPermission() {
        List<Permission> permissions = permissionMapper.selectFatherPermission();
        return permissions;
    }

    @Override
    public List<Permission> selectAllPermissionByActorId(Integer actorId) {
        List<Permission> permissions = permissionMapper.selectAllPermission();
        List<Integer> sonId = actorAndPermissionMapper.selectPermissionsByActorId(actorId);
        for (int i = 0; i < permissions.size(); i++) {
            List<Permission> childPermission = permissions.get(i).getChildPermission();
            for (int j = 0; j < childPermission.size(); j++) {
                if (sonId.contains(childPermission.get(j).getPermissionId())) {
                    childPermission.get(j).setCheckStatic(true);
                }
            }
        }
        return permissions;
    }

    @Override
    public boolean updatePermissionWithActor(Integer actorId, Integer[] permissionIds) {
        boolean b = actorAndPermissionMapper.deletePermissionFromActor(actorId);
        if (permissionIds == null) {
            return b;
        }
        List<ActorPermission> actorPermissions = new ArrayList<>(permissionIds.length);
        for (int i = 0; i < permissionIds.length; i++) {
            ActorPermission actorWithPermission = new ActorPermission();
            actorWithPermission.setActorId(actorId);
            actorWithPermission.setPermissionId(permissionIds[i]);
            actorPermissions.add(actorWithPermission);
        }
        for (ActorPermission actorPermission : actorPermissions) {
            actorAndPermissionMapper.addPermissionToActor(actorPermission);
        }
        return b;
    }

    @Override
    public List<Permission> selectAllPermission() {
        List<Permission> permissions = permissionMapper.selectAllPermission();
        return permissions;
    }

    @Override
    public boolean insertPermission(Permission permission) {
        if (permission.getPermissionFatherId() == -1) {
            permission.setPermissionFatherId(null);
        }
        if (permissionMapper.insertPermission(permission)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean updatePermission(Permission permission) {
        if (permission.getPermissionFatherId() == -1) {
            permission.setPermissionFatherId(null);
        }
        if (permissionMapper.updatePermission(permission)) {
            return true;
        }
        return false;
    }

    @Override
    public Permission selectPermissionById(Integer permissionId) {
        Permission permission = permissionMapper.selectPermissionById(permissionId);
        return permission;
    }

    @Override
    public boolean deletePermission(Integer permissionId) {
        boolean b = permissionMapper.deletePermissionById(permissionId);
        return b;
    }

    @Override
    public boolean deleteCheckedPermission(Integer[] permissionIds) {
        for (int i = 0; i < permissionIds.length; i++) {
            if (!permissionMapper.deletePermissionById(permissionIds[i])) {
                return false;
            }
        }
        return true;
    }
}
