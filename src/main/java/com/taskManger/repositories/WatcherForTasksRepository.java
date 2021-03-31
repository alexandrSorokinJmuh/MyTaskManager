package com.taskManger.repositories;

import com.taskManger.DataStorage.DataStorage;
import com.taskManger.entities.Entity;
import com.taskManger.entities.ListOfTasks;
import com.taskManger.entities.Tasks;
import com.taskManger.entities.WatcherForTasks;
import com.taskManger.exception.EntityNotFoundException;
import com.taskManger.exception.UUIDIsNotUniqueException;
import lombok.NonNull;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

public class WatcherForTasksRepository implements Repository{
    private final DataStorage dataStorage;

    public WatcherForTasksRepository(DataStorage dataStorage) {
        this.dataStorage = dataStorage;
    }

    public List<WatcherForTasks> getAll() {
        return this.dataStorage.getWatcherForTasksList();
    }

    public WatcherForTasks getEntity(String uuid) throws NullPointerException, UUIDIsNotUniqueException, EntityNotFoundException {

        throw new UnsupportedOperationException(WatcherForTasks.class.toString() + " can't find instance by uuid because you need two uuid to find (task uuid, user uuid)");
    }

    public List<WatcherForTasks> getEntitiesByContact(String contactUuid) throws NullPointerException, UUIDIsNotUniqueException, EntityNotFoundException {
        if (contactUuid == null)
            throw new NullPointerException("contactUUID must be not null");

        List<WatcherForTasks> result = this.findBy((Entity x) -> ((WatcherForTasks)x).getContactUuid().equals(contactUuid));

        return result;
    }

    public List<WatcherForTasks> getEntitiesByUser(String userUuid) throws NullPointerException, UUIDIsNotUniqueException, EntityNotFoundException {
        if (userUuid == null)
            throw new NullPointerException("userUUID must be not null");

        List<WatcherForTasks> result = this.findBy((Entity x) -> ((WatcherForTasks)x).getUserUuid().equals(userUuid));


        return result;
    }

    public WatcherForTasks getEntity(String contactUuid, String userUuid) throws NullPointerException, UUIDIsNotUniqueException, EntityNotFoundException {
        if (contactUuid == null)
            throw new NullPointerException("contactUUID must be not null");
        if (userUuid == null)
            throw new NullPointerException("userUUID must be not null");
        List<WatcherForTasks> result = this.findBy((Entity x) -> {
            return ((WatcherForTasks)x).getContactUuid().equals(contactUuid) && ((WatcherForTasks)x).getUserUuid().equals(userUuid);
        });
        if(result.size() == 0){
            throw new EntityNotFoundException(String.format("Entity with contact UUID %s and user UUID %s not found", contactUuid, userUuid));
        }
        if (result.size() > 1){
            throw new UUIDIsNotUniqueException("Instance with those UUIDs is not unique");
        }
        return result.get(0);
    }

    public WatcherForTasks create(Entity entity) throws NullPointerException, UUIDIsNotUniqueException, IllegalArgumentException{
        if(entity == null)
            throw new NullPointerException("Entity must be not null");

        if(entity.getClass() != WatcherForTasks.class)
            throw new IllegalArgumentException("Entity should be instance of class " + WatcherForTasks.class.toString());

        WatcherForTasks watcherForTasksEntity = (WatcherForTasks) entity;

        List<WatcherForTasks> result = findBy((Entity x) -> {
            return ((WatcherForTasks)x).getContactUuid().equals(watcherForTasksEntity.getContactUuid()) &&
                    ((WatcherForTasks)x).getUserUuid().equals(watcherForTasksEntity.getUserUuid());
        });
        if(result.size() > 0)
            throw new UUIDIsNotUniqueException("Entity with those UUIDs already exists");
        List<WatcherForTasks> changedList = dataStorage.getWatcherForTasksList();
        changedList.add((WatcherForTasks) entity);
        dataStorage.setWatcherForTasksList(changedList);
        return watcherForTasksEntity;
    }

    public WatcherForTasks update(Entity entity)throws NullPointerException, EntityNotFoundException {
        throw new UnsupportedOperationException("WatcherForTasks have not method update");
    }

    public void delete(String uuid) throws NullPointerException, UUIDIsNotUniqueException, EntityNotFoundException{
        throw new UnsupportedOperationException(WatcherForTasks.class.toString() +
                " can't delete instance by uuid because you need two uuid to find (task uuid, user uuid)");
    }

    public void delete(String contactUuid, String userUuid) throws NullPointerException, UUIDIsNotUniqueException, EntityNotFoundException{

        WatcherForTasks toDelete = this.getEntity(contactUuid, userUuid);
        List<WatcherForTasks> changedList = dataStorage.getWatcherForTasksList();
        changedList.remove(toDelete);
        dataStorage.setWatcherForTasksList(changedList);
    }

    public void deleteByContact(String contactUuid) throws NullPointerException, UUIDIsNotUniqueException, EntityNotFoundException{

        List<WatcherForTasks> toDelete = this.getEntitiesByContact(contactUuid);
        List<WatcherForTasks> changedList = dataStorage.getWatcherForTasksList();
        changedList.removeAll(toDelete);
        dataStorage.setWatcherForTasksList(changedList);
    }
    public void deleteByUser(String userUuid) throws NullPointerException, UUIDIsNotUniqueException, EntityNotFoundException{

        List<WatcherForTasks> toDelete = this.getEntitiesByUser(userUuid);
        List<WatcherForTasks> changedList = dataStorage.getWatcherForTasksList();
        changedList.removeAll(toDelete);
        dataStorage.setWatcherForTasksList(changedList);
    }
    @Override
    public List<WatcherForTasks> findBy(Predicate<Entity> condition) throws NullPointerException{
        if (condition == null)
            throw new NullPointerException("Condition must be not null");
        List<WatcherForTasks> watcherForTasks = dataStorage.getWatcherForTasksList();
        List<WatcherForTasks> resultList = new LinkedList<>();
        for(WatcherForTasks i : watcherForTasks){
            if(condition.test(i)){
                resultList.add(i);
            }
        }
        return resultList;
    }

}
