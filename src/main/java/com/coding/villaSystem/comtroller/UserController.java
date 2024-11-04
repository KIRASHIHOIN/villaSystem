package com.coding.villaSystem.comtroller;

import com.coding.villaSystem.entity.Actor;
import com.coding.villaSystem.entity.Permission;
import com.coding.villaSystem.entity.User;
import com.coding.villaSystem.service.ActorService;
import com.coding.villaSystem.service.PermissionService;
import com.coding.villaSystem.service.UserService;
import com.coding.villaSystem.vo.UserPageVo;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Resource
    UserService userService;
    @Resource
    PermissionService permissionService;
    @Resource
    ActorService actorService;

    @RequestMapping("/goLogin")
    public String goLogin(HttpServletRequest request, HttpSession session) {
        User result = userService.noLogin(request, session);
        if (result == null) {
            return "login";
        } else {
            List<Permission> permissions = permissionService.selectPermissionByActorId(result.getActorId());
            session.setAttribute("permissions", permissions);
            session.setAttribute("user", result);
            session.setAttribute("account", result.getUserAccount());
            session.setAttribute("id", result.getUserId());
            return "system_index";
        }
    }

    @RequestMapping("/goRegister")
    public String goRegister() {
        return "register";
    }

    @RequestMapping("/goLogout")
    public String goLogout(HttpServletRequest request, HttpServletResponse response) {
        boolean b = userService.logOut(request, response);
        return "redirect:/user/goLogin";
    }

    @RequestMapping("goUserCenter")
    public String goUserCenter() {
        return "down/user_center";
    }

    @RequestMapping("goInformation")
    public String goInformation() {
        return "down/information";
    }

    @RequestMapping("/doLogin")
    public String doLogin(User user, HttpSession session, String kaptcha, String noLogin, HttpServletResponse response, Model m) {
        String kaptchaCode = (String) session.getAttribute("verifyCode");
        if (!kaptcha.equals(kaptchaCode) || !kaptcha.toLowerCase().equals(kaptchaCode)) {
            m.addAttribute("error", "验证码错误");
            return "login";
        }
        boolean accountResult = userService.checkAccount(user.getUserAccount());
        if (!accountResult) {
            m.addAttribute("error", "账号必须4 ~ 6位");
            return "login";
        }

        boolean passwordResult = userService.checkPassword(user.getUserPassword());
        if (!passwordResult) { //密码格式有误！
            m.addAttribute("error", "密码必须3 ~ 7位");
            return "login";
        }

        User result = userService.checkLogin(user);
        if (result == null) {
            m.addAttribute("error", "账号或密码有误");
            return "login";
        }
        if (noLogin != null) {
            Cookie idCookie = new Cookie("idCookie", result.getUserAccount());
            Cookie passwordCookie = new Cookie("passwordCookie", result.getUserPassword());
            idCookie.setMaxAge(60 * 60 * 24 * 7);
            passwordCookie.setMaxAge(60 * 60 * 24 * 7);
            response.addCookie(idCookie);
            response.addCookie(passwordCookie);
        }
        List<Permission> permissions = permissionService.selectPermissionByActorId(result.getActorId());
        session.setAttribute("permissions", permissions);
        session.setAttribute("user", result);
        session.setAttribute("account", result.getUserAccount());
        session.setAttribute("id", result.getUserId());
        return "system_index";
    }

    @RequestMapping("/doRegister")
    public String doRegister(User user, String userPasswordConfirm, HttpSession session, String kaptcha, Model m) {
        if (userService.selectUserByUserAccount(user.getUserAccount()) != null) {
            m.addAttribute("error", "用户名已存在");
            return "register";
        }
        boolean accountResult = userService.checkAccount(user.getUserAccount());
        if (!accountResult) {
            m.addAttribute("error", "账号必须4 ~ 6位");
            return "register";
        }

        boolean passwordResult = userService.checkPassword(user.getUserPassword());
        if (!passwordResult) { //密码格式有误！
            m.addAttribute("error", "密码必须3 ~ 7位");
            return "register";
        }
        boolean passwordConfirmResult = userService.checkPasswordTwice(user.getUserPassword(), userPasswordConfirm);
        if (!passwordConfirmResult) { //两次密码输入不一致
            m.addAttribute("error", "两次密码输入不一致");
            return "register";
        }
        boolean adminSuccess = userService.register(user);
        if (!adminSuccess) { //注册失败
            m.addAttribute("error", "注册失败");
            return "register";
        }
        m.addAttribute("adminMessages", user.getUserAccount());
        return "login";
    }

    @RequestMapping("/goResetPassword")
    public String goResetPassword(Integer userId, Model m) {
        m.addAttribute("userId", userId);
        return "down/password_set";
    }

    @RequestMapping("/resetPassword")
    public String resetPassword(Integer userId, String previousPassword, String modifiedPassword, String revisedPassword, HttpServletRequest request, HttpServletResponse response, Model m) {
        Integer i = userService.resetPassword(userId, previousPassword, modifiedPassword, revisedPassword);
        String mis = "";
        if (i == 2) {
            mis = "原密码输入错误";
        }
        if (i == 3) {
            mis = "密码输入长度错误";
        }
        if (i == 4) {
            mis = "密码未发生变更";
        }
        if (i == 5) {
            mis = "两次确认密码输入不一致";
        }
        if (i == 0) {
            mis = "未知错误";
        }
        if (i != 1) {
            m.addAttribute("error", mis);
//            return "redirect:/user/goResetPassword";
            return "down/password_set";
        }
        boolean b = userService.logOut(request, response);
        return "redirect:/user/goLogin";
    }

    @RequestMapping("/goAddUser")
    public String goAddUser(Model m) {
        List<Actor> actors = actorService.selectAllActor();
        User user = new User();
        m.addAttribute("user", user);
        m.addAttribute("actors", actors);
        return "/user/user_add";
    }

    @RequestMapping("/addUser")
    public String addUser(User user, String userPasswordConfirm, Model m) {
        if (userService.checkAccount(user.getUserAccount()) && userService.checkPassword(user.getUserPassword()) && userService.checkPasswordTwice(user.getUserPassword(), userPasswordConfirm)) {
            if (userService.newUser(user)) {
                return "redirect:/user/goIndex";
            }
        }
        List<Actor> actors = actorService.selectAllActor();
        m.addAttribute("actors", actors);
        m.addAttribute("error", "添加失败");
        m.addAttribute("user", user);
        m.addAttribute("userPasswordConfirm", userPasswordConfirm);
        return "/user/user_add";
    }

    @RequestMapping("/goIndex")
    public String goIndex(String condition,
                          @DateTimeFormat(pattern = "yyyy-MM-dd")
                          LocalDate start,
                          @DateTimeFormat(pattern = "yyyy-MM-dd")
                          LocalDate end,
                          Integer currentPage,
                          Model m) {
        UserPageVo userPageVo = userService.selectUserByPage(condition, start, end, currentPage);
        m.addAttribute("userPageVo", userPageVo);
        return "/user/user_index";
    }
    @RequestMapping("/toUpdateUser")
    public String goUpdateUser(Integer userId, Model m) {
        User user = userService.selectUserByUserId(userId);
        m.addAttribute("user", user);
        return "/user/user_update";
    }

    @RequestMapping("/updateUser")
    public String updateUser(User user, String userPasswordConfirm, Model m) {
        if (userService.checkAccount(user.getUserAccount()) && userService.checkPassword(user.getUserPassword()) && userService.checkPasswordTwice(user.getUserPassword(), userPasswordConfirm)) {
            if (userService.updateUser(user)) {
                return "redirect:/user/goIndex";
            }
        }
        m.addAttribute("error", "修改失败");
        m.addAttribute("user", user);
        m.addAttribute("userPasswordConfirm", userPasswordConfirm);
        return "/user/user_update";
    }
    @RequestMapping("/toUpdateUserAndRole")
    public String toUpdateUserAndRole(Integer userId, Model m) {
        User user = userService.selectUserByUserId(userId);
        List<Actor> actors = actorService.selectAllActor();
        m.addAttribute("user", user);
        m.addAttribute("actors", actors);
        return "/user/user_role_update";
    }

    @RequestMapping("/updateUserAndRole")
    public String updateUserAndRole(User user, Model m) {
        if (userService.updateUser(user)) {
            return "redirect:/user/goIndex";
        }
        m.addAttribute("error", "修改失败");
        m.addAttribute("user", user);
        return "/user/user_role_update";
    }
    @ResponseBody
    @RequestMapping("/doDelete")
    public boolean doDelete(Integer userId) {
        boolean result = userService.deleteUser(userId);
        return result;
    }

    @ResponseBody
    @RequestMapping("/deleteMany")
    public boolean deleteMany(@RequestParam("ids[]") Integer[] ids) {
        boolean result = userService.deleteCheckedUser(ids);
        return result;
    }
}
