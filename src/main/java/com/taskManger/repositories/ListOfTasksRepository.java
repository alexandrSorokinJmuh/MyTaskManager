package com.taskManger.repositories;

import com.taskManger.DataStorage.DataStorage;
import com.taskManger.entities.Entity;
import com.taskManger.entities.ListOfTasks;
import com.taskManger.exception.EntityNotFoundException;
import com.taskManger.exception.UUIDIsNotUniqueException;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

public class ListOfTasksRepository implements Repository{

    private final DataStorage dataStorage;

    public ListOfTasksRepository(DataStorage dataStorage) {
        this.dataStorage = dataStorage;
    }


    public List<ListOfTasks> getAll() {
        return this.dataStorage.getListOfTasks();
    }

    public ListOfTasks getEntity(String uuid) throws NullPointerException, UUIDIsNotUniqueException, EntityNotFoundException {
        if (uuid == null)
            throw new NullPointerException("UUID must be not null");
        List<ListOfTasks> result = this.findBy((Entity x) -> {
            return ((ListOfTasks)x).getUuid().equals(uuid);
        });
        if(result.size() == 0){
            throw new EntityNotFoundException(String.format("Entity with UUID %s not found", uuid));
        }
        if (result.size() > 1){
            throw new UUIDIsNotUniqueException("UUID is not unique");
        }
        return result.get(0);
    }

    public ListOfTasks create(Entity entity) throws NullPointerException, UUIDIsNotUniqueException, IllegalArgumentException{
        if(entity == null)
            throw new NullPointerException("Entity must be not null");

        if(entity.getClass() != ListOfTasks.class)
            throw new IllegalArgumentException("Entity should be instance of class " + ListOfTasks.class.toString());

        ListOfTasks listOfTasksEntity = (ListOfTasks) entity;

        List<ListOfTasks> result = findBy((Entity x) -> {
            return ((ListOfTasks)x).getUuid().equals(listOfTasksEntity.getUuid());
        });
        if(result.size() > 0)
            throw new UUIDIsNotUniqueException(String.format("Entity with UUID %s already exists", listOfTasksEntity.getUuid()));
        List<ListOfTasks> changedList = dataStorage.getListOfTasks();
        changedList.add((ListOfTasks) entity);
        dataStorage.setListOfTasks(changedList);
        return listOfTasksEntity;
    }

    public ListOfTasks update(Entity entity) throws NullPointerException, EntityNotFoundException {
        if(entity == null)
            throw new NullPointerException("Entity must be not null");

        if(entity.getClass() != ListOfTasks.class)
            throw new IllegalArgumentException("Entity should be instance of class " + ListOfTasks.class.toString());

        List<ListOfTasks> changedList = dataStorage.getListOfTasks();
        int index = changedList.indexOf(entity);
        if(index == -1)
            throw new EntityNotFoundException("Entity not found");

        dataStorage.setListOfTasks(changedList);
        return (ListOfTasks) entity;
    }

    public void delete(String uuid) throws NullPointerException, UUIDIsNotUniqueException, EntityNotFoundException{

        ListOfTasks toDelete = this.getEntity(uuid);
        List<ListOfTasks> changedList = dataStorage.getListOfTasks();
        changedList.remove(toDelete);
        dataStorage.setListOfTasks(changedList);
    }

    @Override
    public List<ListOfTasks> findBy(Predicate<Entity> condition) throws NullPointerException{
        if (condition == null)
            throw new NullPointerException("Condition must be not null");
        List<ListOfTasks> listOfTasks = dataStorage.getListOfTasks();
        List<ListOfTasks> resultList = new LinkedList<>();
        for(ListOfTasks i : listOfTasks){
            if(condition.test(i)){
                resultList.add(i);
            }
        }
        return resultList;
    }

}
