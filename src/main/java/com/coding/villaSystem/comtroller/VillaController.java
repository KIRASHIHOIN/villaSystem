package com.coding.villaSystem.comtroller;

import com.coding.villaSystem.entity.Store;
import com.coding.villaSystem.entity.User;
import com.coding.villaSystem.entity.Villa;
import com.coding.villaSystem.service.OssService;
import com.coding.villaSystem.service.StoreService;
import com.coding.villaSystem.service.UserService;
import com.coding.villaSystem.service.VillaService;
import com.coding.villaSystem.vo.VillaPageVo;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/villa")
public class VillaController {
    @Resource
    VillaService villaService;
    @Resource
    StoreService storeService;
    @Resource
    UserService userService;
    @Resource
    OssService ossService;

    @RequestMapping("/goIndex")
    public String gonIndex(
            String condition,
            @DateTimeFormat(pattern = "yyyy-MM-dd")
            LocalDate start,
            @DateTimeFormat(pattern = "yyyy-MM-dd")
            LocalDate end,
            Integer currentPage,
            Integer storeNumber,
            Model m) {
        VillaPageVo villaPageVo = villaService.getVillaPageVo(condition, start, end, currentPage, storeNumber);
        List<Store> allStore = storeService.getAllStore();
        villaPageVo.setStoreNumber(storeNumber);
        m.addAttribute("villaPageVo", villaPageVo);
        m.addAttribute("stores", allStore);
        return "villa/villa_index";
    }

    @RequestMapping("/goAddVilla")
    public String goAddVilla(Model m) {
        List<Store> allStore = storeService.getAllStore();
        m.addAttribute("stores", allStore);
        return "villa/villa_add";
    }

    @RequestMapping("/addVilla")
    public String addVilla(Villa villa, MultipartFile villaPicture, Model m) {
        String imageUrl = ossService.addHeadImage(villaPicture);
        villa.setVillaImage(imageUrl);
        if (villaService.addVilla(villa)) {
            return "redirect:/villa/goIndex";
        }
        List<Store> allStore = storeService.getAllStore();
        m.addAttribute("stores", allStore);
        m.addAttribute("error", "添加失败");
        return "villa/villa_add";
    }

    @RequestMapping("/goUpdateVilla")
    public String goUpdateVilla(Integer villaId, Model m) {
        Villa villa = villaService.getVillaById(villaId);
        List<Store> allStore = storeService.getAllStore();
        User user = userService.selectUserByUserId(villa.getVillaAddUserId());
        m.addAttribute("villa", villa);
        m.addAttribute("stores", allStore);
        m.addAttribute("user", user);
        return "villa/villa_update";
    }

    @RequestMapping("/updateVilla")
    public String updateVilla(Villa villa, MultipartFile villaPicture, String villaOldImage, Model m) {
        if (villaPicture.getOriginalFilename() != null && !villaPicture.getOriginalFilename().isEmpty()) {
            String imageUrl = ossService.addHeadImage(villaPicture);
            villa.setVillaImage(imageUrl);
        } else {
            villa.setVillaImage(villaOldImage);
        }
        if (villaService.updateVilla(villa)) {
            return "redirect:/villa/goIndex";
        }
        m.addAttribute("error", "修改失败");
        return "villa/villa_update";
    }

    @ResponseBody
    @RequestMapping("/doDelete")
    public boolean doDelete(Integer villaId) {
        boolean result = villaService.deleteVilla(villaId);
        return result;
    }

    @ResponseBody
    @RequestMapping("/deleteMany")
    public boolean deleteMany(@RequestParam("ids[]") Integer[] ids) {
        boolean result = villaService.deleteCheckedVilla(ids);
        return result;
    }
}
