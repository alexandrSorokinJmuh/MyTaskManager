package com.taskManger.views;

import com.taskManger.controllers.TaskController;
import com.taskManger.controllers.UserController;
import com.taskManger.entities.User;

import java.util.Scanner;

public class MainMenuView {

    private final int mainMenuActionCount = 5;
    private UserController userController;
    private TaskController taskController;
    private User user;

    public MainMenuView(UserController userController, TaskController taskController, User user) {
        this.userController = userController;
        this.taskController = taskController;
        this.user = user;
    }

    public void mainMenu(){
        programBody:
        {
            while (true) {
                System.out.println("Choose action to do:");
                System.out.println("1. Tasks");
                System.out.println("2. List of tasks");
                System.out.println("3. Find task");
                System.out.println("4. Log out");
                System.out.println("5. Exit");

                Scanner in = new Scanner(System.in);
                System.out.print("Input a number: ");
                int num = in.nextInt();

                if (num >= 1 && num <= mainMenuActionCount) {
                    switch (num){
                        case 1:
                            break programBody;
                        case 2:
                            break programBody;
                        case 3:
                            break programBody;
                        case 4:

                            break programBody;
                        case 5:
                            break programBody;

                    }
                } else {
                    System.out.flush();
                    System.out.println("\n\nWrong input!!!\n");
                }
            }
        }
    }
}
