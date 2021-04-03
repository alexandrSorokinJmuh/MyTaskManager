package com.taskManger.controllers;

import com.taskManger.entities.ListOfTasks;
import com.taskManger.entities.TaskForUser;
import com.taskManger.entities.Tasks;
import com.taskManger.entities.User;
import com.taskManger.exception.EntityNotFoundException;
import com.taskManger.exception.UUIDIsNotUniqueException;
import com.taskManger.services.ListOfTaskService;
import com.taskManger.services.TaskService;
import lombok.NonNull;

import java.util.List;

public class ListOfTasksController {

    ListOfTaskService listOfTaskService;

    public ListOfTasksController(ListOfTaskService listOfTaskService) {
        this.listOfTaskService = listOfTaskService;
    }

    public ListOfTasks createNewListOfTasks(@NonNull User user, @NonNull String name) throws UUIDIsNotUniqueException {

        ListOfTasks listOfTasks = listOfTaskService.createNewList(user.getUuid(), name);

        return listOfTasks;
    }

    public TaskForUser addTaskToList(@NonNull User user, @NonNull ListOfTasks listOfTasks, @NonNull Tasks task, @NonNull String name) throws UUIDIsNotUniqueException, EntityNotFoundException {

        TaskForUser result = listOfTaskService.addTaskToList(listOfTasks.getUuid(), user.getUuid(), task.getUuid(), name);

        return result;
    }

    public List<ListOfTasks> getAllListsByUser(@NonNull User user) {
        return listOfTaskService.getAllListsByUser(user.getUuid());
    }



    public List<TaskForUser> getAllTasksByList(@NonNull ListOfTasks listOfTasks){
        return listOfTaskService.getAllTasksByList(listOfTasks.getUuid());
    }

    public void changeName(@NonNull ListOfTasks currentListOfTask, @NonNull String nameNew)  throws UUIDIsNotUniqueException, EntityNotFoundException {
        listOfTaskService.changeName(currentListOfTask.getUuid(), nameNew);
    }
    public void changeTaskForUserName(@NonNull TaskForUser currentTaskForUser, @NonNull String nameNew)  throws UUIDIsNotUniqueException, EntityNotFoundException {
        listOfTaskService.changeTaskForUserName(currentTaskForUser.getUuid(), nameNew);
    }

    public void deleteList(@NonNull ListOfTasks listOfTasks) throws UUIDIsNotUniqueException, EntityNotFoundException {
        listOfTaskService.deleteAllList(listOfTasks.getUuid());
    }

    public void deleteOnlyTaskForUser(@NonNull TaskForUser taskForUser) throws UUIDIsNotUniqueException, EntityNotFoundException {
        listOfTaskService.deleteOnlyTaskForUser(taskForUser.getUuid());
    }

    public void deleteTask(@NonNull TaskForUser taskForUser) throws UUIDIsNotUniqueException, EntityNotFoundException {
        listOfTaskService.deleteTask(taskForUser.getTaskUuid());
    }

    public List<User> getUsersNotInTask(@NonNull TaskForUser taskForUser){
        return listOfTaskService.getUsersNotInTask(taskForUser.getTaskUuid());
    }

    public List<Tasks> getTasksNotInList(@NonNull ListOfTasks listOfTasks) {
        return listOfTaskService.getTasksNotInList(listOfTasks.getUuid());
    }

    public void addUserToTask(@NonNull TaskForUser taskForUser, @NonNull User user) throws UUIDIsNotUniqueException {
        listOfTaskService.createTaskForUser(taskForUser.getListUuid(), taskForUser.getTaskUuid(), user.getUuid(), taskForUser.getName());
    }

}
