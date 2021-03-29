package com.taskManger.repositories;

import com.taskManger.entities.Entity;
import com.taskManger.exception.EntityNotFoundException;
import com.taskManger.exception.UUIDIsNotUniqueException;

import java.util.List;
import java.util.function.Predicate;

public interface Repository {
    void save(Entity entity);
    List<? extends Entity> getAll();
    Entity getEntity(String uuid) throws NullPointerException, UUIDIsNotUniqueException, EntityNotFoundException;
    Entity create(Entity entity) throws NullPointerException, UUIDIsNotUniqueException;
    Entity update(Entity entity) throws NullPointerException, EntityNotFoundException;
    void delete(String uuid) throws UUIDIsNotUniqueException, EntityNotFoundException;
    List<? extends Entity> findBy(Predicate<Entity> condition);
}
