package com.taskManger.services;

import com.taskManger.entities.Tasks;
import com.taskManger.exception.UUIDIsNotUniqueException;
import com.taskManger.repositories.TaskRepository;

import java.util.Date;
import java.util.UUID;

public class TaskService {

    TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }
/*
*
* @NonNull
    String uuid;
    @NonNull
    String name;

    String description = "";
    @JsonSerialize(using= DateSerializer.class)
    Date alert_time = new Date();
    Boolean alert_received = false;
* */

    public Tasks regNewTask(String name, String description, Date alert_time) throws UUIDIsNotUniqueException {

        if (name == null)
            throw new NullPointerException("Task name must be not null");

        if (description == null)
            description = "";

        if (alert_time == null)
            throw new NullPointerException("Alert time must be not null");

        String taskUuid = UUID.randomUUID().toString();
        Tasks task = new Tasks(taskUuid, name, description, alert_time, false);

        taskRepository.create(task);

        return task;
    }

}