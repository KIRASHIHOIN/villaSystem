package com.coding.villaSystem.service.impl;

import com.coding.villaSystem.entity.Villa;
import com.coding.villaSystem.mapper.VillaMapper;
import com.coding.villaSystem.service.VillaService;
import com.coding.villaSystem.vo.VillaPageVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;

@Service
public class VillaServiceImpl implements VillaService {

    @Resource
    private VillaMapper villaMapper;


    @Override
    public VillaPageVo getVillaPageVo(String condition, LocalDate start, LocalDate end, Integer currentPage, Integer storeNumber) {
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
        List<Villa> villas = villaMapper.getVillaWithStore(condition, start, end, storeNumber);
        VillaPageVo villaPageVo = new VillaPageVo();
        PageInfo<Villa> pageInfo = new PageInfo<>(villas);
        if (pageInfo.getPages() == 0) {
            pageInfo.setPages(1);
        }
        villaPageVo.setTotalPage(pageInfo.getPages());
        villaPageVo.setPageList(villas);
        villaPageVo.setCurrentPage(pageInfo.getPageNum());
        villaPageVo.setCondition(condition);
        if (startFlag) {
            villaPageVo.setStart(start);
        }
        if (endFlag) {
            villaPageVo.setEnd(end);
        }
        return villaPageVo;
    }

    @Override
    public Villa getVillaById(Integer villaId) {
        Villa villa = villaMapper.getVillaByVillaId(villaId);
        return villa;
    }

    @Override
    public boolean addVilla(Villa villa) {
        if (villaMapper.addVilla(villa)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean updateVilla(Villa villa) {
        if (villaMapper.updateVilla(villa)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteVilla(Integer villaId) {
        boolean b = villaMapper.deleteVilla(villaId);
        return b;
    }

    @Override
    public boolean deleteCheckedVilla(Integer[] villaIds) {
        for (int i = 0; i < villaIds.length; i++) {
            if (!villaMapper.deleteVilla(villaIds[i])) {
                return false;
            }
        }
        return true;
    }
}
