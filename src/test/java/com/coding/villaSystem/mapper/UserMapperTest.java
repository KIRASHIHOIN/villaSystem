package com.coding.villaSystem.mapper;

import com.coding.villaSystem.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserMapperTest {
    @Resource
    private UserMapper userMapper;

    @Test
    void selectUserByAccountAndPassword() {
    }

    @Test
    void addUser() {
    }

    @Test
    void checkLogin() {
    }

    @Test
    void selectUserByUserId() {
        User user = userMapper.selectUserByUserId(1);
        System.out.println(user);
    }

    @Test
    void updateUserPassword() {
    }

    @Test
    void newUser() {
    }

    @Test
    void selectUserWithActor() {
        List<User> users = userMapper.selectUserWithActor(null,null,null);
        System.out.println(users);
    }
    @Test
    void updateUser(){
        User user = new User();
        user.setUserId(10);
        user.setUserName("李四");
        user.setUserAccount("boss01");
        user.setUserPassword("boss01");
        user.setActorId(0);
        userMapper.updateUser(user);
    }
}