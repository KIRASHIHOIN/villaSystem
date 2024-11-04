package com.coding.villaSystem.comtroller;

import com.coding.villaSystem.entity.City;
import com.coding.villaSystem.entity.Store;
import com.coding.villaSystem.entity.User;
import com.coding.villaSystem.service.CityService;
import com.coding.villaSystem.service.StoreService;
import com.coding.villaSystem.service.UserService;
import com.coding.villaSystem.vo.StorePageVo;
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
@RequestMapping("/store")
public class StoreController {
    @Resource
    private StoreService storeService;
    @Resource
    private CityService cityService;
    @Resource
    private UserService userService;

    @RequestMapping("/goIndex")
    public String goIndex(String condition,
                          @DateTimeFormat(pattern = "yyyy-MM-dd")
                          LocalDate start,
                          @DateTimeFormat(pattern = "yyyy-MM-dd")
                              LocalDate end,
                          Integer currentPage,
                          Integer cityNumber,
                          Model m){
        StorePageVo storePageVo = storeService.getStorePageVo(condition, start, end, currentPage, cityNumber);
        List<City> cities = cityService.selectAllCity();
        storePageVo.setCityNumber(cityNumber);
        m.addAttribute("storePageVo",storePageVo);
        m.addAttribute("cities",cities);
        return "store/store_index";
    }
    @RequestMapping("/goAddStore")
    public String goAddStore(Model m){
        List<City> cities = cityService.selectAllCity();
        m.addAttribute("cities",cities);
        return "store/store_add";
    }
    @RequestMapping("/addStore")
    public String addStore(Store store, Model m){
        if (storeService.addStore(store)) {
            return "redirect:/store/goIndex";
        }
        List<City> cities = cityService.selectAllCity();
        m.addAttribute("cities",cities);
        m.addAttribute("error", "添加失败");
        return "store/store_add";
    }
    @RequestMapping("/goUpdateStore")
    public String goUpdateStore(Integer storeId, Model m){
        Store store = storeService.getStoreById(storeId);
        List<City> cities = cityService.selectAllCity();
        User user = userService.selectUserByUserId(store.getAddUserId());
        m.addAttribute("store",store);
        m.addAttribute("cities",cities);
        m.addAttribute("user",user);
        return "store/store_update";
    }

    @RequestMapping("/updateStore")
    public String updateStore(Store store, Model m){
        if (storeService.updateStore(store)) {
            return "redirect:/store/goIndex";
        }
        m.addAttribute("error", "修改失败");
        return "store/store_update";
    }
    @ResponseBody
    @RequestMapping("/doDelete")
    public boolean doDelete(Integer storeId) {
        boolean result = storeService.deleteStore(storeId);
        return result;
    }

    @ResponseBody
    @RequestMapping("/deleteMany")
    public boolean deleteMany(@RequestParam("ids[]") Integer[] ids) {
        boolean result = storeService.deleteCheckedStore(ids);
        return result;
    }
}
