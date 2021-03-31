package com.taskManger.controllers;

import com.taskManger.entities.ListOfTasks;
import com.taskManger.entities.User;
import com.taskManger.exception.UUIDIsNotUniqueException;
import com.taskManger.services.ListOfTaskService;
import com.taskManger.services.TaskService;
import lombok.NonNull;

import java.util.UUID;

public class ListOfTasksController {

    TaskService taskService;
    ListOfTaskService listOfTaskService;

    public ListOfTasksController(TaskService taskService, ListOfTaskService listOfTaskService) {
        this.taskService = taskService;
        this.listOfTaskService = listOfTaskService;
    }

    public ListOfTasks createNewListOfTasks(@NonNull User user, @NonNull String taskUuid, @NonNull String name) throws UUIDIsNotUniqueException {

        ListOfTasks listOfTasks = listOfTaskService.registerNewTask(user.getUuid(), user.getUuid(), taskService.firstTask(user).getUuid(), name);

        return listOfTasks;
    }


}
