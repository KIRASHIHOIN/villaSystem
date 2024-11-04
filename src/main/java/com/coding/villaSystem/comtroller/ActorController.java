package com.coding.villaSystem.comtroller;

import com.coding.villaSystem.entity.Actor;
import com.coding.villaSystem.entity.Permission;
import com.coding.villaSystem.service.ActorService;
import com.coding.villaSystem.service.PermissionService;
import com.coding.villaSystem.vo.ActorPageVo;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/actor")
public class ActorController {
    @Resource
    ActorService actorService;
    @Resource
    PermissionService permissionService;

    // 跳转到角色管理首页
    @RequestMapping("/goIndex")
    public String goIndex(
            String condition,
            @DateTimeFormat(pattern = "yyyy-MM-dd")
            LocalDate start,
            @DateTimeFormat(pattern = "yyyy-MM-dd")
            LocalDate end,
            Integer currentPage,
            Model m
    ) {
        // 调用service查询角色信息
        ActorPageVo actorPageVo = actorService.selectActorByPage(condition, start, end, currentPage);
        // 将查询到的角色信息添加到model中
        m.addAttribute("actorPageVo", actorPageVo);
        // 返回角色管理首页
        return "/actor/actor_index";
    }
    // 跳转到添加角色页面
    @RequestMapping("toAddActor")
    public String toAddActor(Model m){
        List<Permission> permissions = permissionService.selectAllPermission();
        m.addAttribute("permissions", permissions);
        return "/actor/actor_add";
    }
    // 添加角色
    @RequestMapping("addActor")
    public String addActor(Actor actor,Integer[] permissionIds, Model m){
        if (actorService.insertActor(actor,permissionIds)) {
            return "redirect:/actor/goIndex";
        }
        m.addAttribute("error", "添加失败");
        m.addAttribute("actor", actor);
        m.addAttribute("permissionIds", permissionIds);
        return "/actor/actor_add";
    }
    // 跳转到修改角色页面
    @RequestMapping("toUpdateActor")
    public String toUpdateActor(Integer actorId, Model m){
        Actor actor = actorService.selectActorById(actorId);
        m.addAttribute("actor", actor);
        return "/actor/actor_update";
    }
    // 修改角色
    @RequestMapping("updateActor")
    public String updateActor(Actor actor, Model m){
        if (actorService.updateActor(actor)) {
            return "redirect:/actor/goIndex";
        }
        m.addAttribute("error", "修改失败");
        return "/actor/actor_update";
    }
    // 删除角色
    @ResponseBody
    @RequestMapping("/doDelete")
    public boolean doDelete(Integer actorId) {
        boolean result = actorService.deleteActor(actorId);
        return result;
    }

    // 批量删除角色
    @ResponseBody
    @RequestMapping("/deleteMany")
    public boolean deleteMany(@RequestParam("ids[]") Integer[] ids) {
        boolean result = actorService.deleteCheckedActor(ids);
        return result;
    }
}