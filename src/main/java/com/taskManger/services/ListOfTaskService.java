package com.taskManger.services;

import com.taskManger.entities.Entity;
import com.taskManger.entities.ListOfTasks;
import com.taskManger.entities.Tasks;
import com.taskManger.exception.EntityNotFoundException;
import com.taskManger.exception.UUIDIsNotUniqueException;
import com.taskManger.repositories.ListOfTasksRepository;
import com.taskManger.repositories.TaskRepository;
import lombok.NonNull;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class ListOfTaskService {

    TaskRepository taskRepository;
    ListOfTasksRepository listOfTasksRepository;

    public ListOfTaskService(TaskRepository taskRepository, ListOfTasksRepository listOfTasksRepository) {
        this.taskRepository = taskRepository;
        this.listOfTasksRepository = listOfTasksRepository;
    }


    public ListOfTasks registerNewTask(@NonNull String creatorUuid, @NonNull String userUuid, @NonNull String taskUuid, @NonNull String name) throws UUIDIsNotUniqueException {

        if (name == null)
            throw new NullPointerException("List of Tasks name must be not null");


        String listOfTasksUuid = UUID.randomUUID().toString();
        ListOfTasks listOfTasks = new ListOfTasks(listOfTasksUuid, creatorUuid, userUuid, taskUuid, name);

        listOfTasksRepository.create(listOfTasks);

        return listOfTasks;
    }

    public ListOfTasks addTaskToList(@NonNull String userUuid, @NonNull String taskUuid, @NonNull String name) throws UUIDIsNotUniqueException {

        if (name == null)
            throw new NullPointerException("List of Tasks name must be not null");

        List<ListOfTasks> listOfTasks = listOfTasksRepository.getEntitiesByName(name);
        ListOfTasks e1 = listOfTasks.get(0);
        String listOfTaskUuid = UUID.randomUUID().toString();
        ListOfTasks listOfTasksNew = new ListOfTasks(listOfTaskUuid, e1.getCreatorUuid(), userUuid, taskUuid, name);

        listOfTasksRepository.create(listOfTasksNew);

        return listOfTasksNew;
    }

    public ListOfTasks changeTask(@NonNull String listOfTaskUuid, @NonNull String taskUuidNew) throws UUIDIsNotUniqueException, EntityNotFoundException {
        ListOfTasks listOfTasks = listOfTasksRepository.getEntity(listOfTaskUuid);
        listOfTasks.setTaskUuid(taskUuidNew);
        listOfTasksRepository.update(listOfTasks);
        return listOfTasks;
    }

    public void deleteTask(@NonNull String listOfTaskUuid) throws UUIDIsNotUniqueException, EntityNotFoundException {
        listOfTasksRepository.delete(listOfTaskUuid);
    }

    public void deleteAllList(@NonNull String name) throws UUIDIsNotUniqueException, EntityNotFoundException {
        List<ListOfTasks> listOfTasks = listOfTasksRepository.getEntitiesByName(name);
        for(ListOfTasks i : listOfTasks){
            listOfTasksRepository.delete(i.getUuid());
        }
    }
}
