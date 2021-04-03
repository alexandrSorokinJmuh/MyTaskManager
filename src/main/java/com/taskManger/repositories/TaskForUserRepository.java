package com.taskManger.repositories;

import com.taskManger.DataStorage.DataStorage;
import com.taskManger.entities.Entity;
import com.taskManger.entities.ListOfTasks;
import com.taskManger.entities.TaskForUser;
import com.taskManger.exception.EntityNotFoundException;
import com.taskManger.exception.UUIDIsNotUniqueException;
import lombok.NonNull;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

public class TaskForUserRepository implements Repository{

    private final DataStorage dataStorage;

    public TaskForUserRepository(DataStorage dataStorage) {
        this.dataStorage = dataStorage;
    }

    public List<TaskForUser> getAll() {
        return this.dataStorage.getTaskForUserList();
    }

    public TaskForUser getEntity(String uuid) throws NullPointerException, UUIDIsNotUniqueException, EntityNotFoundException {
        if (uuid == null)
            throw new NullPointerException("UUID must be not null");
        List<TaskForUser> result = this.findBy((Entity x) -> {
            return ((TaskForUser)x).getUuid().equals(uuid);
        });
        if(result.size() == 0){
            throw new EntityNotFoundException(String.format("Entity with UUID %s not found", uuid));
        }
        if (result.size() > 1){
            throw new UUIDIsNotUniqueException("UUID is not unique");
        }
        return result.get(0);
    }

    public List<TaskForUser> getEntitiesByName(@NonNull String name) throws NullPointerException {

        List<TaskForUser> result = this.findBy((Entity x) -> ((TaskForUser)x).getName().equals(name));

        return result;
    }

    public List<TaskForUser> getEntitiesByTask(@NonNull String taskUuid) throws NullPointerException {

        List<TaskForUser> result = this.findBy((Entity x) -> ((TaskForUser)x).getListUuid().equals(taskUuid));

        return result;
    }

    public List<TaskForUser> getEntitiesByList(@NonNull String listUuid) throws NullPointerException {

        List<TaskForUser> result = this.findBy((Entity x) -> ((TaskForUser)x).getListUuid().equals(listUuid));

        return result;
    }



    public TaskForUser create(Entity entity) throws NullPointerException, UUIDIsNotUniqueException, IllegalArgumentException{
        if(entity == null)
            throw new NullPointerException("Entity must be not null");

        if(entity.getClass() != TaskForUser.class)
            throw new IllegalArgumentException("Entity should be instance of class " + TaskForUser.class.toString());

        TaskForUser taskForUserEntity = (TaskForUser) entity;

        List<TaskForUser> result = findBy((Entity x) -> {
            return ((TaskForUser)x).getUuid().equals(taskForUserEntity.getUuid());
        });
        if(result.size() > 0)
            throw new UUIDIsNotUniqueException(String.format("Entity with UUID %s already exists", taskForUserEntity.getUuid()));
        List<TaskForUser> changedList = dataStorage.getTaskForUserList();
        changedList.add((TaskForUser) entity);
        dataStorage.setTaskForUserList(changedList);
        return taskForUserEntity;
    }

    public TaskForUser update(Entity entity) throws NullPointerException, EntityNotFoundException, UUIDIsNotUniqueException {
        if(entity == null)
            throw new NullPointerException("Entity must be not null");

        if(entity.getClass() != TaskForUser.class)
            throw new IllegalArgumentException("Entity should be instance of class " + TaskForUser.class.toString());

        List<TaskForUser> changedList = dataStorage.getTaskForUserList();
        String uuid = ((TaskForUser) entity).getUuid();
        TaskForUser taskForUserEntity = this.getEntity(uuid);
        int index = changedList.indexOf(taskForUserEntity);
        if(index == -1)
            throw new EntityNotFoundException("Entity not found");
        else
            changedList.set(index, (TaskForUser) entity);
        dataStorage.setTaskForUserList(changedList);
        return (TaskForUser) entity;
    }

    public void delete(String uuid) throws NullPointerException, UUIDIsNotUniqueException, EntityNotFoundException{

        TaskForUser toDelete = this.getEntity(uuid);
        List<TaskForUser> changedList = dataStorage.getTaskForUserList();
        changedList.remove(toDelete);
        dataStorage.setTaskForUserList(changedList);
    }

    @Override
    public List<TaskForUser> findBy(Predicate<Entity> condition) throws NullPointerException{
        if (condition == null)
            throw new NullPointerException("Condition must be not null");
        List<TaskForUser> tasks = dataStorage.getTaskForUserList();
        List<TaskForUser> resultList = new LinkedList<>();
        for(TaskForUser i : tasks){
            if(condition.test(i)){
                resultList.add(i);
            }
        }
        return resultList;
    }

    public List<String> getUsersUuidInTask(@NonNull String taskUuid) throws NullPointerException {

        List<TaskForUser> taskForUsers = this.findBy((Entity taskForUser) -> ((TaskForUser)taskForUser).getTaskUuid().equals(taskUuid));
        List<String> result = new LinkedList<>();
        for(TaskForUser taskForUser : taskForUsers){
            result.add(taskForUser.getUserUuid());
        }

        return result;
    }

    public List<String> getTasksUuidInList(@NonNull String listOfTaskUuid) {

        List<TaskForUser> taskForUsers = this.findBy((Entity taskForUser) -> ((TaskForUser)taskForUser).getListUuid().equals(listOfTaskUuid));
        List<String> result = new LinkedList<>();
        for(TaskForUser taskForUser : taskForUsers){
            result.add(taskForUser.getTaskUuid());
        }

        return result;
    }
}
