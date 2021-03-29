package com.taskManger.controllers;

import com.taskManger.entities.Tasks;
import com.taskManger.entities.User;
import com.taskManger.exception.UUIDIsNotUniqueException;
import com.taskManger.exception.UsernameNotUniqueException;
import com.taskManger.services.TaskService;
import com.taskManger.services.UserService;

import java.util.Date;

public class TaskController {
    TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    public Tasks createNewTask(String name, String description, Date alert_time) throws UUIDIsNotUniqueException {

        Tasks tasks = taskService.regNewTask(name, description, alert_time);

        return tasks;
    }
}
