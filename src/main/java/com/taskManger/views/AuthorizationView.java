package com.taskManger.views;

import com.taskManger.controllers.UserController;
import com.taskManger.entities.User;
import com.taskManger.exception.UUIDIsNotUniqueException;
import com.taskManger.exception.UsernameNotUniqueException;
import com.taskManger.views.results.AuthorizationViewResult;

import java.io.IOException;
import java.util.Scanner;

public class AuthorizationView {

    private final int mainMenuActionCount = 3;
    private UserController userController;

    public AuthorizationView(UserController userController) {
        this.userController = userController;
    }

    public AuthorizationViewResult mainMenu() {
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
                    // signIn();
                    return AuthorizationViewResult.SIGN_IN;
                case 2:
                    // signUp();
                    return AuthorizationViewResult.SING_UP;
                case 3:
                    // exit;
                    return AuthorizationViewResult.EXIT;
            }
        } else {
            System.out.println("\n\nWrong input!!!\n");
            return AuthorizationViewResult.WRONG_INPUT;
        }
        return AuthorizationViewResult.EXIT;
    }

    public AuthorizationViewResult signIn() {
        System.out.println("Input account username and password:");


        Scanner in = new Scanner(System.in);
        System.out.println("username: ");
        String username = in.next();
        System.out.println("password: ");
        String password = in.next();

        User user = userController.singIn(username, password);

        if (user == null) {
            System.out.println("\n\nWrong username or password\n");
            return AuthorizationViewResult.WRONG_USERNAME_OR_PASSWORD;
        } else {
            return AuthorizationViewResult.LOGIN_SUCCESS;
        }
    }

    public AuthorizationViewResult signUp() {
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
            return AuthorizationViewResult.REGISTRATION_SUCCESS;
        } catch (UUIDIsNotUniqueException | UsernameNotUniqueException e) {
            System.out.println("This username is already exists");
            return AuthorizationViewResult.USERNAME_IS_EXISTS;
        }

    }

}
