package com.coding.villaSystem.service;

import com.coding.villaSystem.entity.City;
import com.coding.villaSystem.vo.CityPageVo;

import java.time.LocalDate;
import java.util.List;

public interface CityService {

    CityPageVo selectCityByPage(String condition, LocalDate start, LocalDate end, Integer currentPage);

    City selectCityById(Integer cityId);
    List<City> selectAllCity();
    boolean addCity(City city);
    boolean updateCity(City city);
    boolean deleteCity(Integer cityId);
    boolean deleteCheckedCity(Integer[] cityIds);
}
