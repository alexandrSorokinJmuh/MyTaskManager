package com.taskManger;

import com.taskManger.DataStorage.DataStorage;
import com.taskManger.controllers.ListOfTasksController;
import com.taskManger.controllers.TaskController;
import com.taskManger.controllers.UserController;
import com.taskManger.entities.*;
import com.taskManger.exception.EntityNotFoundException;
import com.taskManger.exception.JsonValidationException;
import com.taskManger.exception.UUIDIsNotUniqueException;
import com.taskManger.exception.UsernameNotUniqueException;
import com.taskManger.repositories.*;
import com.taskManger.services.JSONService;
import com.taskManger.services.ListOfTaskService;
import com.taskManger.services.TaskService;
import com.taskManger.services.UserService;
import com.taskManger.views.*;

import java.io.IOException;
import java.util.*;

public class Main {



    public static void main(String[] args) {

        DataStorage dataStorage = DataStorage.getInstance();


        // Repositories
        UserRepository userRepository = new UserRepository(dataStorage);
        TaskRepository taskRepository = new TaskRepository(dataStorage);
        WatcherForTasksRepository watcherForTasksRepository = new WatcherForTasksRepository(dataStorage);
        TaskForUserRepository taskForUserRepository = new TaskForUserRepository(dataStorage);
        ListOfTasksRepository listOfTasksRepository = new ListOfTasksRepository(dataStorage);


        // Services
        UserService userService = new UserService(userRepository);
        JSONService jsonService = new JSONService(dataStorage);
        TaskService taskService = new TaskService(userRepository, taskRepository, watcherForTasksRepository, taskForUserRepository);
        ListOfTaskService listOfTaskService = new ListOfTaskService(userRepository, taskRepository, listOfTasksRepository, taskForUserRepository);



        // Controllers
        UserController userController = new UserController(userService);
        TaskController taskController = new TaskController(taskService);
        ListOfTasksController listOfTasksController = new ListOfTasksController(listOfTaskService);


        // Import json data
        TestMethods testMethods = new TestMethods();
        testMethods.testImport(jsonService);
//        testMethods.testExport(dataStorage, jsonService, userController, taskController, listOfTasksController);

        //Views
        AuthorizationView authorizationView = new AuthorizationView(userController);
        MainMenuView mainMenuView = new MainMenuView(userController, taskController, null);
        TaskView taskView = new TaskView(userController, taskController, null ,null);
        ListOfTasksView listOfTasksView = new ListOfTasksView(userController, taskController, listOfTasksController);
        FindTaskView findTaskView = new FindTaskView(userController, taskController, listOfTasksController);

        ViewResolver viewResolver = new ViewResolver(authorizationView, mainMenuView, listOfTasksView, findTaskView, taskView);



        // Run application
        viewResolver.authorizationViewResponse(null);
        testMethods.justExport(jsonService);


//        testMethods.testImport(jsonService);
    }
    static class TestMethods{
        public void testImport(JSONService jsonService) {
            try {
                jsonService.importJson("G:\\javaProj\\MyTaskManager\\src\\main\\resources\\auto_created.json");
            } catch (JsonValidationException e) {
                e.printStackTrace();
            }
        }

        public void justExport(JSONService jsonService){
            try {
                jsonService.exportJson("src/main/resources/auto_created.json");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void testExport(DataStorage dataStorage, JSONService jsonService, UserController userController, TaskController taskController, ListOfTasksController listOfTasksController) {
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
                try {
                    listOfTasks.add(listOfTasksController.createNewListOfTasks(userList.get(i), "name" + i));
                } catch (UUIDIsNotUniqueException e) {
                    e.printStackTrace();
                }
            }

            List<TaskForUser> taskForUserList = new LinkedList<>();
            for (int i = 0; i < 10; i++) {
                try {
                    taskForUserList.add(listOfTasksController.addTaskToList(userList.get(i), listOfTasks.get(i), tasksList.get(i), "name" + i));
                } catch (UUIDIsNotUniqueException e) {
                    e.printStackTrace();
                } catch (EntityNotFoundException e) {
                    e.printStackTrace();
                }
            }

            dataStorage.setWatcherForTasksList(watcherForTasks);


            try {
                jsonService.exportJson("src/main/resources/auto_created.json");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
