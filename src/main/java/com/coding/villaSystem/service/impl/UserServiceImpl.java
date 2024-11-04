package com.coding.villaSystem.service.impl;



import com.coding.villaSystem.entity.User;
import com.coding.villaSystem.mapper.UserMapper;
import com.coding.villaSystem.service.UserService;
import com.coding.villaSystem.vo.UserPageVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    UserMapper userMapper;

    @Override
    public boolean checkAccount(String userAccount) {
        if (userAccount.length() >= 4 && userAccount.length() <= 6) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean checkPassword(String adminPassword) {
        if (adminPassword.length() >= 3 && adminPassword.length() <= 7) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public User checkLogin(User user) {
        User result = userMapper.selectUserByAccountAndPassword(user);
        if (result == null) {
            return result;
        }
/*        if (result.getUserName() != null) {
           if (result.getAdminLevel() == null) {
               result.setAdminLevel(3);
            }
       }*/
        return result;
    }

    @Override
    public User selectUserByUserId(Integer userId) {
        User user = userMapper.selectUserByUserId(userId);
        return user;
    }

    @Override
    public User noLogin(HttpServletRequest request, HttpSession session) {
        String id = "";
        String password = "";
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (int i = 0; i < cookies.length; i++) {

                if ("idCookie".equals(cookies[i].getName())) {
                    id = cookies[i].getValue();
                }
                if ("passwordCookie".equals(cookies[i].getName())) {
                    password = cookies[i].getValue();
                }
            }
        }

        User user = new User();
        user.setUserAccount(id);
        user.setUserPassword(password);
        User result = userMapper.checkLogin(user);
        if (result == null) {
            return result;
        }
        return result;
    }

    @Override
    public boolean logOut(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (int i = 0; i < cookies.length; i++) {
                if ("idCookie".equals(cookies[i].getName())) {
                    cookies[i].setMaxAge(0);
                    response.addCookie(cookies[i]);
                }
                if ("passwordCookie".equals(cookies[i].getName())) {
                    cookies[i].setMaxAge(0);
                    response.addCookie(cookies[i]);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean register(User user) {
        boolean result = userMapper.addUser(user);
        if (result) {
            return true;
        }
        return false;
    }

    @Override
    public boolean checkPasswordTwice(@Param("adminPassword") String adminPassword, @Param("adminPasswordConfirm") String adminPasswordConfirm) {
        if (adminPassword.equals(adminPasswordConfirm)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Integer resetPassword(Integer userId, String previousPassword, String modifiedPassword, String revisedPassword) {
        User beforeUser = userMapper.selectUserByUserId(userId);
        if (!beforeUser.getUserPassword().equals(previousPassword)) {
            return 2;
        }
        if (!checkPassword(previousPassword) || !checkPassword(modifiedPassword) || !checkPassword(revisedPassword)) {
            return 3;
        }
        if (!checkPasswordTwice(modifiedPassword, revisedPassword)) {
            return 5;
        }
        if (previousPassword.equals(modifiedPassword) || previousPassword.equals(revisedPassword)) {
            return 4;
        }
        beforeUser.setUserPassword(revisedPassword);
        if (userMapper.updateUserPassword(beforeUser)) {
            return 1;
        }
        return 0;
    }

    @Override
    public boolean newUser(User user) {
        if (checkAccount(user.getUserAccount()) && checkPassword(user.getUserPassword())) {
            userMapper.newUser(user);
            return true;
        }
        return false;
    }

    @Override
    public UserPageVo selectUserByPage(String condition, LocalDate start, LocalDate end, Integer currentPage) {
        // 定义开始和结束日期标志位
        boolean startFlag = true;
        boolean endFlag = true;
        // 如果开始日期为空，则设置开始日期为1970-01-01
        if (start == null) {
            start = LocalDate.of(1970, 1, 1);
            startFlag = false;
        }
        // 如果结束日期为空，则设置结束日期为当前日期
        if (end == null) {
            end =  LocalDate.now();
            endFlag = false;
        }
        // 如果开始日期在结束日期之后，则交换开始日期和结束日期
        LocalDate temp = null;
        if (start.isAfter(end)) {
            temp = start;
            start = end;
            end = temp;
        }
        // 如果当前页为空，则设置当前页为1
        if (currentPage == null) {
            currentPage = 1;
        }
        // 使用分页插件，设置当前页，每页显示5条记录
        PageHelper.startPage(currentPage, 5);
        // 根据条件查询角色信息
        LocalDate trueEnd = end.plusDays(1);
        List<User> users = userMapper.selectUserWithActor(condition, start, trueEnd);
        // 创建演员分页信息vo
        UserPageVo userPageVo = new UserPageVo();
        // 将查询到的角色信息赋值给角色分页信息vo
        userPageVo.setPageList(users);
        // 将演员信息转换为分页信息
        PageInfo<User> pi = new PageInfo<>(users);
        if (pi.getPages() == 0) {
            pi.setPages(1);
        }
        // 设置当前页
        userPageVo.setCurrentPage(pi.getPageNum());
        // 设置总页数
        userPageVo.setTotalPage(pi.getPages());
        // 设置查询条件
        userPageVo.setCondition(condition);
        // 如果开始日期不为空，则设置开始日期
        if (startFlag) {
            userPageVo.setStart(start);
        }
        // 如果结束日期不为空，则设置结束日期
        if (endFlag) {
            userPageVo.setEnd(end);
        }
        // 返回演员分页信息vo
        return userPageVo;
    }

    @Override
    public boolean updateUser(User user) {
        boolean b = userMapper.updateUser(user);
        if (!b) {
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteUser(Integer userId) {
        boolean b = userMapper.deleteUser(userId);
        return b;
    }

    @Override
    public boolean deleteCheckedUser(Integer[] userIds) {
        for (int i = 0; i < userIds.length; i++) {
            if (!userMapper.deleteUser(userIds[i])) {
                return false;
            }
        }
        return true;
    }

    @Override
    public User selectUserByUserAccount(String userAccount) {
        User user = userMapper.selectUserByUserAccount(userAccount);
        return user;
    }
}
