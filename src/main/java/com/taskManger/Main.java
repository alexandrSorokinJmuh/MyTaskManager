package com.taskManger;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.JsonSchemaGenerator;
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
import com.taskManger.services.JSONService;
import com.taskManger.services.TaskService;
import com.taskManger.services.UserService;
import com.taskManger.views.AuthorizationView;
import org.bouncycastle.util.encoders.Hex;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.function.Predicate;

public class Main {
    public static void main(String[] args) {


        DataStorage dataStorage = DataStorage.getInstance();
        JSONService jsonService = new JSONService(dataStorage);
        UserRepository userRepository = new UserRepository(dataStorage);
        UserService userService = new UserService(userRepository);
        UserController userController = new UserController(userService);

        TaskRepository taskRepository = new TaskRepository(dataStorage);
        TaskService taskService = new TaskService(taskRepository);
        TaskController taskController = new TaskController(taskService);
        try {
            jsonService.import_json("src/main/resources/auto_created.json");
        } catch (JsonValidationException e) {
            e.printStackTrace();
        }


        System.out.println(dataStorage);
        AuthorizationView authorizationView = new AuthorizationView(userController);

        authorizationView.mainMenu();

//        try {
//            jsonService.import_json("G:\\javaProj\\MyTaskManager\\src\\main\\resources\\auto_created.json");
//        } catch (JsonValidationException e) {
//            e.printStackTrace();
//        }
/*
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
                tasksList.add(taskController.createNewTask("task" + i, "", new Date()));
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
            listOfTasks.add(new ListOfTasks(UUID.randomUUID().toString(), userList.get(i).getUuid(), tasksList.get(i).getUuid(), "list of task" + i));
        }



        try {
            jsonService.export_json();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(dataStorage);
*/

//        dataStorage.setUserList(userList);
//        dataStorage.saveUserList();
//        System.out.println(String.class.toString());
    }
}
