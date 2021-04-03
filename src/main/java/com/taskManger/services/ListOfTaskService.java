package com.taskManger.services;

import com.taskManger.entities.*;
import com.taskManger.exception.EntityNotFoundException;
import com.taskManger.exception.UUIDIsNotUniqueException;
import com.taskManger.repositories.ListOfTasksRepository;
import com.taskManger.repositories.TaskForUserRepository;
import com.taskManger.repositories.TaskRepository;
import com.taskManger.repositories.UserRepository;
import lombok.NonNull;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class ListOfTaskService {
    UserRepository userRepository;
    TaskRepository taskRepository;
    ListOfTasksRepository listOfTasksRepository;
    TaskForUserRepository taskForUserRepository;

    public ListOfTaskService(@NonNull TaskRepository taskRepository, @NonNull ListOfTasksRepository listOfTasksRepository, @NonNull TaskForUserRepository taskForUserRepository) {
        this.taskRepository = taskRepository;
        this.listOfTasksRepository = listOfTasksRepository;
        this.taskForUserRepository = taskForUserRepository;
    }


    public ListOfTasks createNewList(@NonNull String creatorUuid, @NonNull String name) throws UUIDIsNotUniqueException {
        String listOfTasksUuid = UUID.randomUUID().toString();
        ListOfTasks listOfTasks = new ListOfTasks(listOfTasksUuid, creatorUuid, name);

        listOfTasksRepository.create(listOfTasks);

        return listOfTasks;
    }

    public TaskForUser addTaskToList(@NonNull String listUuid, @NonNull String userUuid, @NonNull String taskUuid, @NonNull String name) throws UUIDIsNotUniqueException, EntityNotFoundException {


        ListOfTasks listOfTasks = listOfTasksRepository.getEntity(listUuid);
        String taskForUserUuid = UUID.randomUUID().toString();
        TaskForUser taskForUser = new TaskForUser(taskForUserUuid, listUuid, userUuid, taskUuid, name);

        TaskForUser taskForUserNew = taskForUserRepository.create(taskForUser);

        return taskForUserNew;
    }

    public TaskForUser changeTask(@NonNull String taskForUserUuid, @NonNull String taskUuidNew) throws UUIDIsNotUniqueException, EntityNotFoundException {
        TaskForUser taskForUser = taskForUserRepository.getEntity(taskForUserUuid);
        taskForUser.setTaskUuid(taskUuidNew);
        listOfTasksRepository.update(taskForUser);
        return taskForUser;
    }

    public void deleteOnlyTaskForUser(@NonNull String taskForUserUuid) throws UUIDIsNotUniqueException, EntityNotFoundException {
        taskForUserRepository.delete(taskForUserUuid);
    }

    public void deleteTask(@NonNull String taskUuid) throws UUIDIsNotUniqueException, EntityNotFoundException {
        taskRepository.delete(taskUuid);
    }

    public void deleteAllList(@NonNull String listUuid) throws UUIDIsNotUniqueException, EntityNotFoundException {
        List<TaskForUser> taskForUsers = taskForUserRepository.getEntitiesByList(listUuid);

        for (TaskForUser i : taskForUsers) {
            taskForUserRepository.delete(i.getUuid());
        }

        listOfTasksRepository.delete(listUuid);
    }


    public List<ListOfTasks> getAllListsByUser(@NonNull String user) {
        return listOfTasksRepository.getListsByCreator(user);
    }

    public List<TaskForUser> getAllTasksByList(@NonNull String listUuid) {
        return taskForUserRepository.getEntitiesByList(listUuid);
    }

    public ListOfTasks changeName(@NonNull String listOfTaskUuid, @NonNull String nameNew) throws UUIDIsNotUniqueException, EntityNotFoundException {

        ListOfTasks listOfTasks = listOfTasksRepository.getEntity(listOfTaskUuid);
        listOfTasks.setName(nameNew);
        listOfTasksRepository.update(listOfTasks);

        return listOfTasks;
    }

    public List<User> getUsersNotInTask(@NonNull String taskUuid) {
        List<User> userList = new LinkedList<>(userRepository.getAll());

        List<String> usersUuid = taskForUserRepository.getUsersUuidInTask(taskUuid);
        try {
            for (String userUuid : usersUuid) {
                User user = userRepository.getEntity(userUuid);
                if(userList.contains(user))
                    userList.remove(user);
            }
        } catch (UUIDIsNotUniqueException | EntityNotFoundException e) {
            e.printStackTrace();
        }

        return userList;
    }

    public void createTaskForUser(@NonNull String listUuid, @NonNull String taskUuid, @NonNull String userUuid, @NonNull String name) throws UUIDIsNotUniqueException {
        String taskForUserUuid = UUID.randomUUID().toString();
        TaskForUser taskForUser = new TaskForUser(taskForUserUuid, listUuid, userUuid, taskUuid, name);

        taskForUserRepository.create(taskForUser);
    }

    public List<TaskForUser> changeTaskForUserName(@NonNull String taskForUserUuid, @NonNull String nameNew) throws UUIDIsNotUniqueException, EntityNotFoundException {
        TaskForUser taskForUser = taskForUserRepository.getEntity(taskForUserUuid);
        List<TaskForUser> taskForUserList = taskForUserRepository.getEntitiesByName(taskForUser.getName());
        for (TaskForUser task : taskForUserList){
            task.setName(nameNew);
            taskForUserRepository.update(taskForUser);
        }


        return taskForUserList;
    }

    public List<Tasks> getTasksNotInList(@NonNull String listOfTaskUuid) {
        List<Tasks> tasksList = new LinkedList<>(taskRepository.getAll());
        List<String> tasksUuid = taskForUserRepository.getTasksUuidInList(listOfTaskUuid);
        try {
            for (String uuid : tasksUuid) {
                Tasks task = taskRepository.getEntity(uuid);
                if(tasksList.contains(task))
                    tasksList.remove(task);
            }
        } catch (UUIDIsNotUniqueException | EntityNotFoundException e) {
            e.printStackTrace();
        }

        return tasksList;
    }
}
