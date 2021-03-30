package com.taskManger.views;

import com.taskManger.controllers.TaskController;
import com.taskManger.controllers.UserController;
import com.taskManger.entities.User;
import com.taskManger.views.results.MainMenuViewResult;
import com.taskManger.views.results.TaskViewResult;

import java.util.Scanner;

public class TaskView {
    private final int mainMenuActionCount = 4;
    private UserController userController;
    private TaskController taskController;
    private User user;

    public TaskView(UserController userController, TaskController taskController, User user) {
        this.userController = userController;
        this.taskController = taskController;
        this.user = user;
    }

    public TaskViewResult mainMenu() {
        System.out.println("Choose action to do:");
        System.out.println("1. Edit task");
        System.out.println("2. Create task");
        System.out.println("3. Delete task");
        System.out.println("4. Exit");

        Scanner in = new Scanner(System.in);
        System.out.print("Input a number: ");
        int num = in.nextInt();

        if (num >= 1 && num <= mainMenuActionCount) {
            switch (num) {
                case 1:
                    // EDIT_TASK
                    return TaskViewResult.EDIT_TASK;
                case 2:
                    // CREATE_TASK
                    return TaskViewResult.CREATE_TASK;
                case 3:
                    // DELETE_TASK
                    return TaskViewResult.DELETE_TASK;
                case 4:
                    // Exit
                    return TaskViewResult.EXIT;
            }
        } else {
            System.out.println("\n\nWrong input!!!\n");
            return TaskViewResult.WRONG_INPUT;
        }
        return TaskViewResult.EXIT;
    }
}
