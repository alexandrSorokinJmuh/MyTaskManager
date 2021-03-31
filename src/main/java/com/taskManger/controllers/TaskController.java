package com.taskManger.controllers;

import com.taskManger.entities.ListOfTasks;
import com.taskManger.entities.Tasks;
import com.taskManger.entities.User;
import com.taskManger.exception.EntityNotFoundException;
import com.taskManger.exception.UUIDIsNotUniqueException;
import com.taskManger.services.TaskService;
import lombok.NonNull;

import java.util.Date;
import java.util.List;

public class TaskController {
    TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    public Tasks createNewTask(String name, String creatorUuid, String description, Date alert_time) throws UUIDIsNotUniqueException {

        Tasks tasks = taskService.registerNewTask(name, creatorUuid, description, alert_time);

        return tasks;
    }
    public List<Tasks> getAllTaskByUser(User user){
        return taskService.getAllTaskByUser(user);
    }

    public void changeName(@NonNull Tasks tasks, @NonNull String nameNew) throws UUIDIsNotUniqueException, EntityNotFoundException {
        taskService.changeName(tasks.getUuid(), nameNew);
    }

    public void changeAlertTime(@NonNull Tasks tasks, @NonNull String descriptionNew) throws UUIDIsNotUniqueException, EntityNotFoundException {
        taskService.changeDescription(tasks.getUuid(), descriptionNew);
    }

    public void changeAlertTime(@NonNull Tasks tasks, @NonNull Date alertTime) throws UUIDIsNotUniqueException, EntityNotFoundException {
        taskService.changeAlertTime(tasks.getUuid(), alertTime);
    }

    public void deleteTask(@NonNull Tasks tasks) throws UUIDIsNotUniqueException, EntityNotFoundException {
        taskService.deleteTask(tasks.getUuid());
    }

}
