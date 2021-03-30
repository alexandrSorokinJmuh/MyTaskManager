package com.taskManger.views;

import com.taskManger.controllers.UserController;
import com.taskManger.entities.User;
import com.taskManger.exception.UUIDIsNotUniqueException;
import com.taskManger.exception.UsernameNotUniqueException;

import java.io.IOException;
import java.util.Scanner;

public class AuthorizationView {

    private final int mainMenuActionCount = 3;
    private UserController userController;

    public AuthorizationView(UserController userController) {
        this.userController = userController;
    }

    public void mainMenu() {
        programBody:
        {

            while (true) {
                System.out.println("Choose action to do:");
                System.out.println("1. Sign in");
                System.out.println("2. Sign up");
                System.out.println("3. Exit");

                Scanner in = new Scanner(System.in);
                System.out.print("Input a number: ");
                int num = in.nextInt();

                if (num >= 1 && num <= mainMenuActionCount) {
                    switch (num) {
                        case 1:
                            signIn();
                            break programBody;
                        case 2:
                            signUp();
                            break programBody;
                        case 3:
                            break programBody;
                    }
                } else {
                    System.out.flush();
                    System.out.println("\n\nWrong input!!!\n");
                }
            }


        }
        System.out.println("Goodbye!!!");

    }

    public void signIn() {
        System.out.println("Input account username and password:");


        Scanner in = new Scanner(System.in);
        System.out.println("username: ");
        String username = in.next();
        System.out.println("password: ");
        String password = in.next();

        User user = userController.singIn(username, password);

        if (user == null) {
            System.out.println("\n\nWrong username or password\n");
            mainMenu();
        } else {
            return;
        }
    }

    public void signUp() {
        System.out.println("Input fields:");

        Scanner in = new Scanner(System.in);

        System.out.println("username: ");
        String username = in.next();

        System.out.println("password: ");
        String password = in.next();

        System.out.println("firstName: ");
        String firstName = in.next();

        System.out.println("lastName: ");
        String lastName = in.next();

        System.out.println("phone: ");
        String phone = in.next();


        User user = null;
        try {
            user = userController.singUp(username, password, firstName, lastName, phone);
            mainMenu();
        } catch (UUIDIsNotUniqueException e) {
            e.printStackTrace();
        } catch (UsernameNotUniqueException e) {
            System.out.println("This username is already exists");
            mainMenu();
        }

    }
}
