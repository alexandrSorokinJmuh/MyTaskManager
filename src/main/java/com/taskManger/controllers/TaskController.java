package com.taskManger.controllers;

import com.taskManger.entities.Tasks;
import com.taskManger.exception.UUIDIsNotUniqueException;
import com.taskManger.services.TaskService;

import java.util.Date;

public class TaskController {
    TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    public Tasks createNewTask(String name, String creatorUuid, String description, Date alert_time) throws UUIDIsNotUniqueException {

        Tasks tasks = taskService.registerNewTask(name, creatorUuid, description, alert_time);

        return tasks;
    }
}
