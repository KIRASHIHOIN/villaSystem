package com.coding.villaSystem.service.impl;

import com.coding.villaSystem.entity.City;
import com.coding.villaSystem.entity.Store;
import com.coding.villaSystem.mapper.CityMapper;
import com.coding.villaSystem.mapper.StoreMapper;
import com.coding.villaSystem.service.StoreService;
import com.coding.villaSystem.vo.StorePageVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;

@Service
public class StoreServiceImpl implements StoreService {

    @Resource
    private StoreMapper storeMapper;
    @Resource
    private CityMapper cityMapper;

    @Override
    public StorePageVo getStorePageVo(String condition, LocalDate start, LocalDate end, Integer currentPage, Integer cityNumber) {
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
        List<Store> stores = storeMapper.getStoreWithCity(condition, start, end, cityNumber);
        StorePageVo storePageVo = new StorePageVo();
        PageInfo<Store> pageInfo = new PageInfo<>(stores);
        if (pageInfo.getPages() == 0) {
            pageInfo.setPages(1);
        }
        storePageVo.setTotalPage(pageInfo.getPages());
        storePageVo.setPageList(stores);
        storePageVo.setCurrentPage(pageInfo.getPageNum());
        storePageVo.setCondition(condition);
        if (startFlag) {
            storePageVo.setStart(start);
        }
        // 如果结束日期不为空，则设置结束日期
        if (endFlag) {
            storePageVo.setEnd(end);
        }
        return storePageVo;
    }

    @Override
    public Store getStoreById(Integer storeId) {
        Store store = storeMapper.getStoreByStoreId(storeId);
        return store;
    }

    @Override
    public boolean addStore(Store store) {
        City city = cityMapper.getCityByCityId(store.getStoreCityId());
        store.setStoreCity(city.getCityName());
        if (storeMapper.addStore(store)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean updateStore(Store store) {
        if (storeMapper.updateStore(store)) {
            return true;
        }
        return false;
    }

    @Override
    public List<Store> getAllStore() {
        List<Store> allStore = storeMapper.getAllStore();
        return allStore;
    }

    @Override
    public boolean deleteStore(Integer storeId) {
        boolean b = storeMapper.deleteStore(storeId);
        return b;
    }

    @Override
    public boolean deleteCheckedStore(Integer[] storeIds) {
        for (int i = 0; i < storeIds.length; i++) {
            if (!storeMapper.deleteStore(storeIds[i])) {
                return false;
            }
        }
        return true;
    }
}
