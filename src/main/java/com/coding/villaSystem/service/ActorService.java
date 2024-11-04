package com.coding.villaSystem.service;

import com.coding.villaSystem.entity.Actor;
import com.coding.villaSystem.vo.ActorPageVo;

import java.time.LocalDate;
import java.util.List;

public interface ActorService {
    ActorPageVo selectActorByPage(String condition, LocalDate start, LocalDate end, Integer currentPage);

    Actor selectActorById(Integer actorId);

    List<Actor> selectAllActor();

    boolean insertActor(Actor actor,Integer[] permissionIds);

    boolean updateActor(Actor actor);
    boolean deleteActor(Integer actorId);
    boolean deleteCheckedActor(Integer[] actorIds);
}
