package com.taskManger.controllers;

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


    public List<Tasks> getAllTaskByUser(@NonNull User user){
        return taskService.getAllTaskByUser(user.getUuid());
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

    public List<Tasks> getAll() {
        return taskService.getAll();
    }

    public List<Tasks> getWatchedTasks(User user) throws UUIDIsNotUniqueException, EntityNotFoundException {
        return taskService.getWatchedTasks(user.getUuid());
    }

    public Tasks getTaskByUuid(String taskUuid) throws UUIDIsNotUniqueException, EntityNotFoundException {
        return taskService.getTaskByUuid(taskUuid);
    }

    public List<Tasks> getTaskWithNameLike(String namePattern, List<Tasks> tasksList) {
        return taskService.getTaskWithNameLike(namePattern, tasksList);

    }

    public List<Tasks> getTaskWithName(String namePattern, List<Tasks> tasksList) {
        return taskService.getTaskWithName(namePattern, tasksList);
    }

    public List<Tasks> getTasksWithAlertTimeBefore(Date alertTimePattern, List<Tasks> tasksList) {
        return taskService.getTasksWithAlertTimeBefore(alertTimePattern, tasksList);
    }

    public List<Tasks> getTasksWithAlertTimeAfter(Date alertTimePattern, List<Tasks> tasksList) {
        return taskService.getTasksWithAlertTimeAfter(alertTimePattern, tasksList);
    }

    public List<Tasks> getTasksWithAlertTimeEquals(Date alertTimePattern, List<Tasks> tasksList) {
        return taskService.getTasksWithAlertTimeEquals(alertTimePattern, tasksList);
    }

    public List<Tasks> getAvailableTasks(User user, List<Tasks> tasksList) {
        return taskService.getAvailableTasks(user, tasksList);
    }

    public List<Tasks> getTasksWithCreatorLike(String userNamePattern, String firstNamePattern, String lastNamePattern, List<Tasks> tasksList) {
        return taskService.getTasksWithCreatorLike(userNamePattern, firstNamePattern, lastNamePattern, tasksList);
    }

    public List<User> getUsersNotWatchingTask(Tasks currentTask) {
        return taskService.getUsersNotWatchingTask(currentTask);
    }

    public void addWatcherForTask(User user, Tasks currentTask) throws UUIDIsNotUniqueException {
        taskService.addWatcherForTask(user.getUuid(), currentTask.getUuid());
    }
}
