package com.coding.villaSystem.service.impl;

import com.coding.villaSystem.entity.City;
import com.coding.villaSystem.entity.Permission;
import com.coding.villaSystem.mapper.CityMapper;
import com.coding.villaSystem.service.CityService;
import com.coding.villaSystem.vo.CityPageVo;
import com.coding.villaSystem.vo.PermissionPageVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;

@Service
public class CityServiceImpl implements CityService {

    @Resource
    CityMapper cityMapper;

    @Override
    public CityPageVo selectCityByPage(String condition, LocalDate start, LocalDate end, Integer currentPage) {
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
            end = LocalDate.now().plusDays(1);
            endFlag = false;
        }
        // 如果开始日期在结束日期之后，则交换开始日期和结束日期
        LocalDate temp = null;
        if (start.isAfter(end)) {
            temp = start;
            start = end;
            end = temp;
        }
        if (currentPage == null || currentPage < 1) {
            currentPage = 1;
        }
        PageHelper.startPage(currentPage, 5);
        List<City> cities = cityMapper.getCityWithUser(condition, start, end);
        CityPageVo cityPageVo = new CityPageVo();
        PageInfo<City> pi = new PageInfo<>(cities);
        if (pi.getPages() == 0) {
            pi.setPages(1);
        }
        cityPageVo.setPageList(cities);
        cityPageVo.setCurrentPage(pi.getPageNum());
        cityPageVo.setTotalPage(pi.getPages());
        cityPageVo.setCondition(condition);
        if (startFlag) {
            cityPageVo.setStart(start);
        }
        // 如果结束日期不为空，则设置结束日期
        if (endFlag) {
            cityPageVo.setEnd(end);
        }
        return cityPageVo;
    }

    @Override
    public City selectCityById(Integer cityId) {
        City city = cityMapper.getCityByCityId(cityId);
        return city;
    }

    @Override
    public List<City> selectAllCity() {
        List<City> allCity = cityMapper.getAllCity();
        return allCity;
    }

    @Override
    public boolean addCity(City city) {
        if (cityMapper.addCity(city)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean updateCity(City city) {
        if (cityMapper.updateCity(city)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteCity(Integer cityId) {
        boolean b = cityMapper.deleteCity(cityId);
        return b;
    }

    @Override
    public boolean deleteCheckedCity(Integer[] cityIds) {
        for (int i = 0; i < cityIds.length; i++) {
            if (!cityMapper.deleteCity(cityIds[i])) {
                return false;
            }
        }
        return true;
    }
}
