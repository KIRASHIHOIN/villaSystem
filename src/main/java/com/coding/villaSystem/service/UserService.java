package com.coding.villaSystem.service;



import com.coding.villaSystem.entity.User;
import com.coding.villaSystem.vo.UserPageVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;


public interface UserService {

    boolean checkAccount(String userAccount);

    boolean checkPassword(String adminPassword);

    User checkLogin(User user);

    User selectUserByUserId(Integer userId);

    User noLogin(HttpServletRequest request, HttpSession session);

    boolean logOut(HttpServletRequest request, HttpServletResponse response);

    boolean register(User user);

    boolean checkPasswordTwice(String adminPassword,String adminPasswordConfirm);

    Integer resetPassword(Integer userId, String previousPassword, String modifiedPassword, String revisedPassword);

    boolean newUser(User user);

    UserPageVo selectUserByPage(String condition, LocalDate start, LocalDate end, Integer currentPage);

    boolean updateUser(User user);
    boolean deleteUser(Integer userId);
    boolean deleteCheckedUser(Integer[] userIds);

    User selectUserByUserAccount(String userAccount);
}
