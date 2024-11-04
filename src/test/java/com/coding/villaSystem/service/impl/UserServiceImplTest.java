package com.coding.villaSystem.service.impl;

import com.coding.villaSystem.entity.User;
import com.coding.villaSystem.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class UserServiceImplTest {


    @Resource
    private UserService userService;
    @Test
    void checkAccount() {
    }

    @Test
    void checkPassword() {
    }

    @Test
    void checkLogin() {
    }

    @Test
    void selectUserByUserId() {
    }

    @Test
    void noLogin() {
    }

    @Test
    void logOut() {
    }

    @Test
    void register() {
    }

    @Test
    void checkPasswordTwice() {
    }

    @Test
    void resetPassword() {
    }

    @Test
    void newUser() {
    }

    @Test
    void selectUserByPage() {
    }

    @Test
    void updateUser() {
        User user = new User();
        user.setUserId(10);
        user.setUserName("赵六");
        user.setUserAccount("boss01");
        user.setUserPassword("boss01");
        user.setActorId(0);
        userService.updateUser(user);
    }
}