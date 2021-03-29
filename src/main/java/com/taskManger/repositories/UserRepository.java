package com.taskManger.repositories;

import com.taskManger.DataStorage.DataStorage;
import com.taskManger.entities.Entity;
import com.taskManger.entities.ListOfTasks;
import com.taskManger.entities.User;
import com.taskManger.exception.EntityNotFoundException;
import com.taskManger.exception.UUIDIsNotUniqueException;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

public class UserRepository implements Repository{

    private final DataStorage dataStorage;

    public UserRepository(DataStorage dataStorage) {
        this.dataStorage = dataStorage;
    }

    public void save(Entity entity) {

    }

    public List<User> getAll() {
        return this.dataStorage.getUserList();
    }

    public User getEntity(String uuid)  throws NullPointerException, UUIDIsNotUniqueException, EntityNotFoundException {
        if (uuid == null)
            throw new NullPointerException("UUID must be not null");
        List<User> result = this.findBy((Entity x) -> {
            return ((User)x).getUuid().equals(uuid);
        });
        if(result.size() == 0){
            throw new EntityNotFoundException(String.format("Entity with UUID %s not found", uuid));
        }
        if (result.size() > 1){
            throw new UUIDIsNotUniqueException("UUID is not unique");
        }
        return result.get(0);
    }

    public User create(Entity entity) throws NullPointerException, UUIDIsNotUniqueException, IllegalArgumentException {
        if(entity == null)
            throw new NullPointerException("Entity must be not null");

        if(entity.getClass() != User.class)
            throw new IllegalArgumentException("Entity should be instance of class " + User.class.toString());

        User userEntity = (User) entity;

        List<User> result = findBy((Entity x) -> {
            return ((User)x).getUuid().equals(userEntity.getUuid());
        });
        if(result.size() > 0)
            throw new UUIDIsNotUniqueException(String.format("Entity with UUID %s already exists", userEntity.getUuid()));
        List<User> changedList = dataStorage.getUserList();
        changedList.add((User) entity);
        dataStorage.setUserList(changedList);
        return (User) entity;
    }

    public User update(Entity entity) throws NullPointerException, EntityNotFoundException  {
        if(entity == null)
            throw new NullPointerException("Entity must be not null");

        if(entity.getClass() != User.class)
            throw new IllegalArgumentException("Entity should be instance of class " + User.class.toString());

        List<User> changedList = dataStorage.getUserList();
        int index = changedList.indexOf(entity);
        if(index == -1)
            throw new EntityNotFoundException("Entity not found");

        dataStorage.setUserList(changedList);
        return (User) entity;
    }

    public void delete(String uuid) throws NullPointerException, UUIDIsNotUniqueException, EntityNotFoundException{
        User toDelete = this.getEntity(uuid);
        List<User> changedList = dataStorage.getUserList();
        changedList.remove(toDelete);
        dataStorage.setUserList(changedList);
    }


    @Override
    public List<User> findBy(Predicate<Entity> condition) throws NullPointerException{
        if (condition == null)
            throw new NullPointerException("Condition must be not null");
        List<User> userList = dataStorage.getUserList();
        List<User> resultList = new LinkedList<>();
        for(User i : userList){
            if(condition.test(i)){
                resultList.add(i);
            }
        }
        return resultList;
    }

}
