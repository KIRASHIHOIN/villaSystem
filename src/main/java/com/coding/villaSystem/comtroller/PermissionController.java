package com.coding.villaSystem.comtroller;

import com.coding.villaSystem.entity.Actor;
import com.coding.villaSystem.entity.Permission;
import com.coding.villaSystem.service.ActorService;
import com.coding.villaSystem.service.PermissionService;
import com.coding.villaSystem.vo.PermissionPageVo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/per")
public class PermissionController {

    @Resource
    private PermissionService permissionService;
    @Resource
    private ActorService actorService;


    @RequestMapping("/goIndex")
    public String goIndex(String condition, Integer currentPage, Integer fatherPermissionId, Model m) {
        PermissionPageVo permissionPageVo = permissionService.selectPermissionByPage(condition, fatherPermissionId, currentPage);
        m.addAttribute("permissionPageVo", permissionPageVo);
        List<Permission> permissions = permissionService.selectFatherPermission();
        m.addAttribute("permissions", permissions);
        return "/permissions/permissions_index";
    }

    @RequestMapping("/toPer")
    public String toPer(Integer actorId, Model m) {
        Actor actor = actorService.selectActorById(actorId);
        m.addAttribute("actor", actor);
        List<Permission> permissions = permissionService.selectAllPermissionByActorId(actorId);
        m.addAttribute("permissions", permissions);
        return "/actor/actor_permissions";
    }

    @RequestMapping("/updatePer")
    public String updatePer(Integer actorId, Integer[] permissionIds, Model m) {
        if (permissionService.updatePermissionWithActor(actorId, permissionIds)) {
            return "redirect:/actor/goIndex";
        }
        Actor actor = actorService.selectActorById(actorId);
        m.addAttribute("actor", actor);
        List<Permission> permissions = permissionService.selectAllPermissionByActorId(actorId);
        m.addAttribute("permissions", permissions);
        return "/actor/actor_permissions";
    }
    @RequestMapping("/toUpdatePer")
    public String toUpdatePer(Integer permissionId, Model m){
        Permission permission = permissionService.selectPermissionById(permissionId);
        m.addAttribute("permission", permission);
        List<Permission> permissionFathers = permissionService.selectFatherPermission();
        m.addAttribute("permissionFathers", permissionFathers);
        return "/permissions/permissions_update";
    }
    @RequestMapping("/updateToPer")
    public String updateToPer(Permission permission, Model m){
        if (permissionService.updatePermission(permission)) {
            return "redirect:/per/goIndex";
        }
        System.out.println(permission);
        List<Permission> permissionFathers = permissionService.selectFatherPermission();
        m.addAttribute("permissionFathers", permissionFathers);
        m.addAttribute("permission", permission);
        m.addAttribute("error", "修改失败");
        return "/permissions/permissions_update";
    }

    @RequestMapping("/toAddPer")
    public String toAddPer(Model m) {
        List<Permission> permissionFathers = permissionService.selectFatherPermission();
        m.addAttribute("permissionFathers", permissionFathers);
        return "/permissions/permissions_add";
    }

    @RequestMapping("/addPer")
    public String addPer(Permission permission, Model m) {
        if (permissionService.insertPermission(permission)) {
            return "redirect:/per/goIndex";
        }
        List<Permission> permissionFathers = permissionService.selectFatherPermission();
        m.addAttribute("permissionFathers", permissionFathers);
        m.addAttribute("error", "添加失败");
        return "/permissions/permissions_add";
    }
    @ResponseBody
    @RequestMapping("/doDelete")
    public boolean doDelete(Integer permissionId) {
        boolean result = permissionService.deletePermission(permissionId);
        return result;
    }

    @ResponseBody
    @RequestMapping("/deleteMany")
    public boolean deleteMany(@RequestParam("ids[]") Integer[] ids) {
        boolean result = permissionService.deleteCheckedPermission(ids);
        return result;
    }
}
