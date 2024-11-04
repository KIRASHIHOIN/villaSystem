package com.coding.villaSystem.mapper;

import com.coding.villaSystem.entity.City;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface CityMapper {
    List<City> getCityWithUser(@Param("condition") String condition,
                               @Param("start") LocalDate start,
                               @Param("end") LocalDate end);

    City getCityByCityId(Integer cityId);

    List<City> getAllCity();

    boolean addCity(City city);

    boolean updateCity(City city);

    boolean deleteCity(Integer cityId);
}
