package com.coding.villaSystem.mapper;

import com.coding.villaSystem.entity.Actor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface ActorMapper {
    List<Actor> selectActorByCondition(
            @Param("condition") String condition,
            @Param("start") LocalDate start,
            @Param("end") LocalDate end
    );
    Actor selectActorById(Integer actorId);
    List<Actor> selectAllActor();
    boolean insertActor(Actor actor);
    Actor selectActorByName(String name);
    boolean updateActor(Actor actor);
    boolean deleteActor(Integer actorId);
}
