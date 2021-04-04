package com.taskManger.services;

import com.taskManger.entities.TaskForUser;
import com.taskManger.entities.Tasks;
import com.taskManger.entities.User;
import com.taskManger.entities.WatcherForTasks;
import com.taskManger.exception.EntityNotFoundException;
import com.taskManger.exception.UUIDIsNotUniqueException;
import com.taskManger.repositories.*;
import com.taskManger.views.ListOfTasksView;
import lombok.NonNull;

import java.util.*;
import java.util.stream.Collectors;

public class TaskService {
    UserRepository userRepository;
    TaskRepository taskRepository;
    WatcherForTasksRepository watcherForTasksRepository;
    TaskForUserRepository taskForUserRepository;

    public TaskService(UserRepository userRepository, TaskRepository taskRepository, WatcherForTasksRepository watcherForTasksRepository, TaskForUserRepository taskForUserRepository) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
        this.watcherForTasksRepository = watcherForTasksRepository;
        this.taskForUserRepository = taskForUserRepository;
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
        List<TaskForUser> taskForUsers = taskForUserRepository.getEntitiesByTask(taskUuid);
        for (TaskForUser i : taskForUsers){
            taskForUserRepository.delete(i.getUuid());
        }

        taskRepository.delete(taskUuid);
    }

    public Tasks firstTask(@NonNull User user){
        String taskUuid = UUID.randomUUID().toString();
        return new Tasks(taskUuid, "Create list of task", user.getUuid(), String.format("create list of task by user: %s (default)", user.getUsername()), new Date(), true);
    }

    public List<Tasks> getAllTaskByUser(@NonNull String user) {
        return taskRepository.getTasksByCreator(user);
    }

    public List<Tasks> getAll() {
        return taskRepository.getAll();
    }

    public List<Tasks> getWatchedTasks(@NonNull String userUuid) throws UUIDIsNotUniqueException, EntityNotFoundException {
        List<WatcherForTasks> watchedTasks = watcherForTasksRepository.getEntitiesByUser(userUuid);
        Set<Tasks> tasksList = new HashSet<>();
        for(WatcherForTasks watcherForTask : watchedTasks){
            Tasks task = taskRepository.getEntity(watcherForTask.getContactUuid());
            tasksList.add(task);
        }
        return new ArrayList<>(tasksList);
    }

    public Tasks getTaskByUuid(String taskUuid) throws UUIDIsNotUniqueException, EntityNotFoundException {
        return taskRepository.getEntity(taskUuid);
    }

    public List<Tasks> getTaskWithNameLike(String namePattern, List<Tasks> tasksList) {
        return tasksList.stream()
                .filter((Tasks task)-> task.getName().equals(namePattern))
                .collect(Collectors.toList());
    }

    public List<Tasks> getTaskWithName(String namePattern, List<Tasks> tasksList) {
        return tasksList.stream()
                .filter((Tasks task)-> task.getName().contains(namePattern))
                .sorted((lhs, rhs) -> {
                    int indexL = lhs.getName().indexOf(namePattern);
                    int indexR = rhs.getName().indexOf(namePattern);
                    return Integer.compare(indexR, indexL);
                })
                .collect(Collectors.toList());
    }
    public List<Tasks> getTasksWithAlertTimeBefore(Date alertTimePattern, List<Tasks> tasksList) {
        return tasksList.stream()
                .filter((Tasks task) -> task.getAlert_time().before(alertTimePattern))
                .collect(Collectors.toList());
    }

    public List<Tasks> getTasksWithAlertTimeAfter(Date alertTimePattern, List<Tasks> tasksList) {
        return tasksList.stream()
                .filter((Tasks task) -> task.getAlert_time().after(alertTimePattern))
                .collect(Collectors.toList());
    }

    public List<Tasks> getTasksWithAlertTimeEquals(Date alertTimePattern, List<Tasks> tasksList) {
        return tasksList.stream()
                .filter((Tasks task) -> task.getAlert_time().equals(alertTimePattern))
                .collect(Collectors.toList());
    }


    public List<Tasks> getAvailableTasks(User user, List<Tasks> tasksList) {
        List<Tasks> resultList = new LinkedList<>();
        try {
            List<Tasks> watchedTasks = getWatchedTasks(user.getUuid());
            for(Tasks task : tasksList) {
                if(watchedTasks.contains(task)) {
                    resultList.add(task);
                }
            }
        } catch (UUIDIsNotUniqueException | EntityNotFoundException e) {
            e.printStackTrace();
        }
        return resultList;
    }

    public List<Tasks> getTasksWithCreatorLike(String userNamePattern, String firstNamePattern, String lastNamePattern, List<Tasks> tasksList) {
        List<Tasks> resultList = new LinkedList<>();
        for(Tasks task : tasksList){
            try {
                User user = userRepository.getEntity(task.getCreatorUuid());
                if(user.getUsername().contains(userNamePattern) && user.getFirstName().contains(firstNamePattern) &&
                        user.getLastName().contains(lastNamePattern))
                    resultList.add(task);

            } catch (UUIDIsNotUniqueException | EntityNotFoundException e) {
                e.printStackTrace();
            }
        }
        return resultList;
    }
}
