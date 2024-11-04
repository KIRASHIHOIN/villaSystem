package com.coding.villaSystem.mapper;

import com.coding.villaSystem.entity.Actor;
import com.coding.villaSystem.entity.ActorPermission;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ActorAndPermissionMapperTest {

    @Resource
    private ActorAndPermissionMapper actorAndPermissionMapper;

    @Test
    void addPermissionToActor() {
        ActorPermission actorPermission = new ActorPermission();
        actorPermission.setActorId(2);
        actorPermission.setPermissionId(4);
        boolean b = actorAndPermissionMapper.addPermissionToActor(actorPermission);
    }

    @Test
    void deletePermissionFromActor() {
        ActorPermission actorPermission = new ActorPermission();
        actorPermission.setActorId(2);
        actorAndPermissionMapper.deletePermissionFromActor(2);
    }

    @Test
    void selectPermissionsByActorId(){
        Actor actor = new Actor();
        actor.setActorId(1);
        List<Integer> integers = actorAndPermissionMapper.selectPermissionsByActorId(1);
        for (int i = 0; i < integers.size(); i++) {
            System.out.println(integers.get(i));
        }
    }
}