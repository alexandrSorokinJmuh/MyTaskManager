package com.taskManger.services;

import com.taskManger.entities.ListOfTasks;
import com.taskManger.entities.Tasks;
import com.taskManger.entities.User;
import com.taskManger.exception.EntityNotFoundException;
import com.taskManger.exception.UUIDIsNotUniqueException;
import com.taskManger.repositories.TaskRepository;
import com.taskManger.repositories.WatcherForTasksRepository;
import lombok.NonNull;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class TaskService {

    TaskRepository taskRepository;
    WatcherForTasksRepository watcherForTasksRepository;

    public TaskService(TaskRepository taskRepository, WatcherForTasksRepository watcherForTasksRepository) {
        this.taskRepository = taskRepository;
        this.watcherForTasksRepository = watcherForTasksRepository;
    }

    public Tasks registerNewTask(String name, String creatorUuid, String description, Date alertTime) throws UUIDIsNotUniqueException {

        if (name == null)
            throw new NullPointerException("Task name must be not null");

        if (description == null)
            description = "";

        if (alertTime == null)
            throw new NullPointerException("Alert time must be not null");

        String taskUuid = UUID.randomUUID().toString();
        Tasks task = new Tasks(taskUuid, name, creatorUuid, description, alertTime, false);

        taskRepository.create(task);

        return task;
    }

    public Tasks changeAlertTime(@NonNull String taskUuid, @NonNull Date alertTime) throws UUIDIsNotUniqueException, EntityNotFoundException {

        Tasks task = taskRepository.getEntity(taskUuid);
        task.setAlert_time(alertTime);
        taskRepository.update(task);

        return task;
    }

    public Tasks changeName(@NonNull String taskUuid, @NonNull String name) throws UUIDIsNotUniqueException, EntityNotFoundException {

        Tasks task = taskRepository.getEntity(taskUuid);
        task.setName(name);
        taskRepository.update(task);

        return task;
    }

    public Tasks changeDescription(@NonNull String taskUuid, @NonNull String description) throws UUIDIsNotUniqueException, EntityNotFoundException {

        Tasks task = taskRepository.getEntity(taskUuid);
        task.setDescription(description);
        taskRepository.update(task);

        return task;
    }

    public void deleteTask(@NonNull String taskUuid) throws UUIDIsNotUniqueException, EntityNotFoundException {
        taskRepository.delete(taskUuid);
    }

    public Tasks firstTask(@NonNull User user){
        String taskUuid = UUID.randomUUID().toString();
        return new Tasks(taskUuid, "Create list of task", user.getUuid(), String.format("create list of task by user: %s (default)", user.getUsername()), new Date(), true);
    }

    public List<Tasks> getAllTaskByUser(User user) {
        return taskRepository.getTasksByCreator(user.getUuid());
    }
}
