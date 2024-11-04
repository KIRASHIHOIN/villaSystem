package com.coding.villaSystem.comtroller;

import com.coding.villaSystem.entity.City;
import com.coding.villaSystem.entity.User;
import com.coding.villaSystem.service.CityService;
import com.coding.villaSystem.service.UserService;
import com.coding.villaSystem.vo.CityPageVo;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.time.LocalDate;

@Controller
@RequestMapping("/city")
public class CityController {

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
                          Model m) {
        CityPageVo cityPageVo = cityService.selectCityByPage(condition, start, end, currentPage);
        m.addAttribute("cityPageVo", cityPageVo);
        return "city/city_index";
    }
    @RequestMapping("/goAddCity")
    public String goAddCity() {
        return "city/city_add";
    }
    @RequestMapping("/addCity")
    public String addCity(City city, Model m) {
        if (cityService.addCity(city)) {
            return "redirect:/city/goIndex";
        }
        m.addAttribute("error", "添加失败");
        return "city/city_add";
    }
    @RequestMapping("/goUpdateCity")
    public String goUpdateCity(Integer cityId, Model m) {
        City city = cityService.selectCityById(cityId);
        User user = userService.selectUserByUserId(city.getAddUserId());
        m.addAttribute("userAccount", user.getUserAccount());
        m.addAttribute("city", city);
        return "city/city_update";
    }
    @RequestMapping("/updateCity")
    public String updateCity(City city, Model m) {
        if (cityService.updateCity(city)) {
            return "redirect:/city/goIndex";
        }
        m.addAttribute("error", "修改失败");
        return "city/city_update";
    }
    @ResponseBody
    @RequestMapping("/doDelete")
    public boolean doDelete(Integer cityId) {
        boolean result = cityService.deleteCity(cityId);
        return result;
    }

    @ResponseBody
    @RequestMapping("/deleteMany")
    public boolean deleteMany(@RequestParam("ids[]") Integer[] ids) {
        boolean result = cityService.deleteCheckedCity(ids);
        return result;
    }
}
