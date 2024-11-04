package com.coding.villaSystem.service.impl;

import com.coding.villaSystem.entity.Actor;
import com.coding.villaSystem.service.ActorService;
import com.coding.villaSystem.vo.ActorPageVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ActorServiceImplTest {


    @Autowired
    // 注入ActorService
    ActorService actorService;

    @Test
    void selectActorByPage() {
        // 根据分页参数查询演员信息
        ActorPageVo actorPageVo = actorService.selectActorByPage(null, null, null, null);
        // 断言查询结果不为空
        assertNotNull(actorPageVo);
    }

    @Test
    void selectActorById() {
        // 根据演员id查询演员信息
        Actor actor = actorService.selectActorById(1);
        // 断言查询结果不为空
        assertNotNull(actor);
        // 打印查询结果
        System.out.println(actor);
    }

    @Test
    void selectAllActor() {
        // 查询所有演员信息
        List<Actor> actors = actorService.selectAllActor();
        // 断言查询结果不为空
        assertNotNull(actors);
    }

    @Test
    void insertActor() {
        // 插入演员信息
    }

    @Test
    void updateActor() {
        // 更新演员信息
    }

    @Test
    void deleteActor() {
        // 根据演员id删除演员信息
        boolean result = actorService.deleteActor(1);
        // 断言删除结果为true
        assertTrue(result);
    }

    @Test
    void deleteCheckedActor() {
        // 根据演员id数组删除演员信息
        Integer[] actorIds = {1, 2, 3};
        boolean result = actorService.deleteCheckedActor(actorIds);
        // 断言删除结果为true
        assertTrue(result);
    }
}