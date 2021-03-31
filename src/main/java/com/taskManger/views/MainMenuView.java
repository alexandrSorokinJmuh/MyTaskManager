package com.taskManger.views;

import com.taskManger.controllers.TaskController;
import com.taskManger.controllers.UserController;
import com.taskManger.entities.User;
import com.taskManger.views.results.MainMenuViewResult;

import java.util.Scanner;

public class MainMenuView {

    private final int mainMenuActionCount = 5;
    private UserController userController;
    private TaskController taskController;
    private User user;

    public void setUser(User user) {
        this.user = user;
    }

    public MainMenuView(UserController userController, TaskController taskController, User user) {
        this.userController = userController;
        this.taskController = taskController;
        this.user = user;
    }

    public MainMenuViewResult mainMenu() {
        System.out.println("\n\nChoose action to do:");
        System.out.println("1. Tasks");
        System.out.println("2. List of tasks");
        System.out.println("3. Find task");
        System.out.println("4. Log out");
        System.out.println("5. Exit");

        Scanner in = new Scanner(System.in);
        System.out.print("Input a number: ");
        int num = in.nextInt();

        if (num >= 1 && num <= mainMenuActionCount) {
            switch (num) {
                case 1:
                    // Tasks
                    return MainMenuViewResult.TASKS;
                case 2:
                    // List of tasks
                    return MainMenuViewResult.LIST_OF_TASKS;
                case 3:
                    // Find task
                    return MainMenuViewResult.FIND_TASK;
                case 4:
                    // Log out
                    return MainMenuViewResult.LOG_OUT;
                case 5:
                    // Exit
                    return MainMenuViewResult.EXIT;
            }
        } else {
            System.out.println("\n\nWrong input!!!\n");
            return MainMenuViewResult.WRONG_INPUT;
        }
        return MainMenuViewResult.EXIT;
    }
}
