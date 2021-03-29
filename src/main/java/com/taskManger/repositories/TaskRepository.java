package com.taskManger.repositories;

import com.taskManger.DataStorage.DataStorage;
import com.taskManger.entities.Entity;
import com.taskManger.entities.ListOfTasks;
import com.taskManger.entities.Tasks;
import com.taskManger.exception.EntityNotFoundException;
import com.taskManger.exception.UUIDIsNotUniqueException;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

public class TaskRepository implements Repository{
    private final DataStorage dataStorage;

    public TaskRepository(DataStorage dataStorage) {
        this.dataStorage = dataStorage;
    }

    public void save(Entity entity) {

    }

    public List<Tasks> getAll() {
        return this.dataStorage.getTasksList();
    }

    public Tasks getEntity(String uuid) throws NullPointerException, UUIDIsNotUniqueException, EntityNotFoundException {
        if (uuid == null)
            throw new NullPointerException("UUID must be not null");
        List<Tasks> result = this.findBy((Entity x) -> {
            return ((Tasks)x).getUuid().equals(uuid);
        });
        if(result.size() == 0){
            throw new EntityNotFoundException(String.format("Entity with UUID %s not found", uuid));
        }
        if (result.size() > 1){
            throw new UUIDIsNotUniqueException("UUID is not unique");
        }
        return result.get(0);
    }

    public Tasks create(Entity entity) throws NullPointerException, UUIDIsNotUniqueException, IllegalArgumentException {
        if(entity == null)
            throw new NullPointerException("Entity must be not null");

        if(entity.getClass() != Tasks.class)
            throw new IllegalArgumentException("Entity should be instance of class " + Tasks.class.toString());

        Tasks tasksEntity = (Tasks) entity;

        List<Tasks> result = findBy((Entity x) -> {
            return ((Tasks)x).getUuid().equals(tasksEntity.getUuid());
        });
        if(result.size() > 0)
            throw new UUIDIsNotUniqueException(String.format("Entity with UUID %s already exists", tasksEntity.getUuid()));
        List<Tasks> changedList = dataStorage.getTasksList();
        changedList.add((Tasks) entity);
        dataStorage.setTasksList(changedList);
        return tasksEntity;
    }

    public Tasks update(Entity entity) throws NullPointerException, EntityNotFoundException {
        if(entity == null)
            throw new NullPointerException("Entity must be not null");

        if(entity.getClass() != Tasks.class)
            throw new IllegalArgumentException("Entity should be instance of class " + Tasks.class.toString());

        List<Tasks> changedList = dataStorage.getTasksList();
        int index = changedList.indexOf(entity);
        if(index == -1)
            throw new EntityNotFoundException("Entity not found");

        dataStorage.setTasksList(changedList);
        return (Tasks) entity;
    }

    public void delete(String uuid) throws NullPointerException, UUIDIsNotUniqueException, EntityNotFoundException{
        Tasks toDelete = this.getEntity(uuid);
        List<Tasks> changedList = dataStorage.getTasksList();
        changedList.remove(toDelete);
        dataStorage.setTasksList(changedList);
    }

    @Override
    public List<Tasks> findBy(Predicate<Entity> condition) {
        if (condition == null)
            throw new NullPointerException("Condition must be not null");
        List<Tasks> tasksList = dataStorage.getTasksList();
        List<Tasks> resultList = new LinkedList<>();
        for(Tasks i : tasksList){
            if(condition.test(i)){
                resultList.add(i);
            }
        }
        return resultList;
    }


}
