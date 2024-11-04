package com.coding.villaSystem.service.impl;

import com.coding.villaSystem.entity.Actor;
import com.coding.villaSystem.entity.ActorPermission;
import com.coding.villaSystem.mapper.ActorAndPermissionMapper;
import com.coding.villaSystem.mapper.ActorMapper;
import com.coding.villaSystem.service.ActorService;
import com.coding.villaSystem.vo.ActorPageVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;

@Service
public class ActorServiceImpl implements ActorService {

    @Resource
    ActorMapper actorMapper;
    @Resource
    ActorAndPermissionMapper actorAndPermissionMapper;
    @Override
    public ActorPageVo selectActorByPage(String condition, LocalDate start, LocalDate end, Integer currentPage) {

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
            end = LocalDate.now();
            endFlag = false;
        }
        // 如果开始日期在结束日期之后，则交换开始日期和结束日期
        LocalDate temp = null;
        if (start.isAfter(end)) {
            temp = start;
            start = end;
            end = temp;
        }
        // 如果当前页为空，则设置当前页为1
        if (currentPage == null) {
            currentPage = 1;
        }
        // 使用分页插件，设置当前页，每页显示5条记录
        PageHelper.startPage(currentPage, 5);
        // 根据条件查询角色信息
        LocalDate trueEnd = end.plusDays(1);
        List<Actor> actors = actorMapper.selectActorByCondition(condition, start, trueEnd);
        // 创建演员分页信息vo
        ActorPageVo actorPageVo = new ActorPageVo();
        // 将查询到的角色信息赋值给角色分页信息vo
        actorPageVo.setPageList(actors);
        // 将演员信息转换为分页信息
        PageInfo<Actor> pi = new PageInfo<>(actors);
        if (pi.getPages() == 0) {
            pi.setPages(1);
        }
        // 设置当前页
        actorPageVo.setCurrentPage(pi.getPageNum());
        // 设置总页数
        actorPageVo.setTotalPage(pi.getPages());
        // 设置查询条件
        actorPageVo.setCondition(condition);
        // 如果开始日期不为空，则设置开始日期
        if (startFlag) {
            actorPageVo.setStart(start);
        }
        // 如果结束日期不为空，则设置结束日期
        if (endFlag) {
            actorPageVo.setEnd(end);
        }
        // 返回演员分页信息vo
        return actorPageVo;
    }

    @Override
    public Actor selectActorById(Integer actorId) {
        Actor actor = actorMapper.selectActorById(actorId);
        return actor;
    }

    @Override
    public List<Actor> selectAllActor() {
        List<Actor> actors = actorMapper.selectAllActor();
        return actors;
    }

    @Override
    public boolean insertActor(Actor actor, Integer[] permissionIds) {
        ActorPermission actorPermission = new ActorPermission();
        if (actorMapper.insertActor(actor)) {
            Actor newActor = actorMapper.selectActorByName(actor.getActorName());
            for (int i = 0; i < permissionIds.length; i++) {
                actorPermission.setActorId(newActor.getActorId());
                actorPermission.setPermissionId(permissionIds[i]);
                actorAndPermissionMapper.addPermissionToActor(actorPermission);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean updateActor(Actor actor) {
        if (actorMapper.updateActor(actor)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteActor(Integer actorId) {
        boolean b = actorMapper.deleteActor(actorId);
        return b;
    }

    @Override
    public boolean deleteCheckedActor(Integer[] actorIds) {
        for (int i = 0; i < actorIds.length; i++) {
            if (!actorMapper.deleteActor(actorIds[i])) {
                return false;
            }
        }
        return true;
    }
}
