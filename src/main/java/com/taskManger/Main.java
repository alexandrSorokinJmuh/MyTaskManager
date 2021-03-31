package com.taskManger;

import com.taskManger.DataStorage.DataStorage;
import com.taskManger.controllers.TaskController;
import com.taskManger.controllers.UserController;
import com.taskManger.entities.ListOfTasks;
import com.taskManger.entities.Tasks;
import com.taskManger.entities.User;
import com.taskManger.entities.WatcherForTasks;
import com.taskManger.exception.JsonValidationException;
import com.taskManger.exception.UUIDIsNotUniqueException;
import com.taskManger.exception.UsernameNotUniqueException;
import com.taskManger.repositories.TaskRepository;
import com.taskManger.repositories.UserRepository;
import com.taskManger.repositories.WatcherForTasksRepository;
import com.taskManger.services.JSONService;
import com.taskManger.services.TaskService;
import com.taskManger.services.UserService;
import com.taskManger.views.AuthorizationView;

import java.io.IOException;
import java.util.*;

public class Main {



    public static void main(String[] args) {

        DataStorage dataStorage = DataStorage.getInstance();
        JSONService jsonService = new JSONService(dataStorage);
        UserRepository userRepository = new UserRepository(dataStorage);
        UserService userService = new UserService(userRepository);
        UserController userController = new UserController(userService);

        WatcherForTasksRepository watcherForTasksRepository = new WatcherForTasksRepository(dataStorage);

        TaskRepository taskRepository = new TaskRepository(dataStorage);
        TaskService taskService = new TaskService(taskRepository, watcherForTasksRepository);
        TaskController taskController = new TaskController(taskService);

        TestMethods testMethods = new TestMethods();
        testMethods.testImport(jsonService);

        AuthorizationView authorizationView = new AuthorizationView(userController);
        authorizationView.mainMenu();


        testMethods.testImport(jsonService);
//        testMethods.testExport(dataStorage, jsonService, userController, taskController);
    }
    static class TestMethods{
        public void testImport(JSONService jsonService) {
            try {
                jsonService.importJson("G:\\javaProj\\MyTaskManager\\src\\main\\resources\\auto_created.json");
            } catch (JsonValidationException e) {
                e.printStackTrace();
            }
        }

        public void testExport(DataStorage dataStorage, JSONService jsonService, UserController userController, TaskController taskController) {
            List<User> userList = new ArrayList<>();
            try {
                for (int i = 0; i < 10; i++) {

                    userList.add(userController.singUp("alesha" + i, "asd", "as", "sd", "123"));

                }
            } catch (UUIDIsNotUniqueException e) {
                e.printStackTrace();
            } catch (UsernameNotUniqueException e) {
                e.printStackTrace();
            }

            List<Tasks> tasksList = new ArrayList<>();
            try {
                for (int i = 0; i < 10; i++) {
                    tasksList.add(taskController.createNewTask("task" + i, userList.get(i).getUuid(), "", new Date()));
                }
            } catch (UUIDIsNotUniqueException e) {
                e.printStackTrace();
            }

            List<WatcherForTasks> watcherForTasks = new LinkedList<>();
            for (int i = 0; i < 10; i++) {
                watcherForTasks.add(new WatcherForTasks(UUID.randomUUID().toString(), userList.get(i).getUuid()));
            }

            List<ListOfTasks> listOfTasks = new LinkedList<>();
            for (int i = 0; i < 10; i++) {
                listOfTasks.add(new ListOfTasks(UUID.randomUUID().toString(), userList.get(i).getUuid(), userList.get(i).getUuid(), tasksList.get(i).getUuid(), "list of task" + i));
            }
            dataStorage.setListOfTasks(listOfTasks);
            dataStorage.setWatcherForTasksList(watcherForTasks);


            try {
                jsonService.exportJson("src/main/resources/auto_created.json");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
