package com.coding.villaSystem.mapper;


import com.coding.villaSystem.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface UserMapper {

    User selectUserByAccountAndPassword(User user);
    boolean addUser(User user);

    @Select("select user_name, user_id, user_account, user_password, actor_id from user where user_account = #{userAccount} and user_password = #{userPassword}")
    User checkLogin(User user);

    User selectUserByUserId(Integer userId);

    boolean updateUserPassword(User user);

    boolean updateUser(User user);

    boolean newUser(User user);

    List<User> selectUserWithActor(@Param("condition") String condition,
                                    @Param("start") LocalDate start,
                                    @Param("end") LocalDate end);
    boolean deleteUser(Integer userId);

    User selectUserByUserAccount(String userAccount);
}
