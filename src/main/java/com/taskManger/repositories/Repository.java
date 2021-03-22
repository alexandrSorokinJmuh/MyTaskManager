package com.taskManger.repositories;

import com.taskManger.entities.Entity;

import java.util.List;

public interface Repository {
    void save(Entity entity);
    List<Entity> getAll();
    Entity getEntity(String uuid);
    Entity create(Entity entity);
    Entity update(Entity entity);
    void delete(String uuid);
    Entity findBy(SelectionCondition<Entity> condition);
}
