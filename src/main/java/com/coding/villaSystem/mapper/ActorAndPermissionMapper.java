package com.coding.villaSystem.mapper;

import com.coding.villaSystem.entity.ActorPermission;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ActorAndPermissionMapper {
    boolean addPermissionToActor(ActorPermission actorPermission);

    boolean deletePermissionFromActor(Integer actorId);

    List<Integer> selectPermissionsByActorId(Integer actorId);
}
