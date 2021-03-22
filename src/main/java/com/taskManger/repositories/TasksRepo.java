package com.taskManger.repositories;

import com.taskManger.entities.Entity;
import com.taskManger.entities.Tasks;

import java.util.List;

public class TasksRepo implements Repository{

    public void save(Entity entity) {

    }

    public List<Entity> getAll() {
        return null;
    }

    public Entity getEntity(String uuid) {
        return null;
    }

    public Entity create(Entity entity) {
        return null;
    }

    public Entity update(Entity entity) {
        return null;
    }

    public void delete(String uuid) {

    }

    @Override
    public Tasks findBy(SelectionCondition<Entity> condition) {
        return null;
    }

}
