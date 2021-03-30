package com.taskManger.views;

import com.taskManger.views.results.AuthorizationViewResult;

public class ViewResolver {
    AuthorizationView authorizationView;
    MainMenuView mainMenuView;

    public void authorizationResponse(){
        AuthorizationViewResult result = AuthorizationViewResult.REGISTRATION_SUCCESS;
        while (result != AuthorizationViewResult.EXIT && result != AuthorizationViewResult.LOGIN_SUCCESS){
            result = authorizationView.mainMenu();
            switch (result){
                case SIGN_IN:
                    result = authorizationView.signIn();
                    break;
                case SING_UP:
                    result = authorizationView.signUp();
                    break;
                case WRONG_INPUT:
                case USERNAME_IS_EXISTS:
                case REGISTRATION_SUCCESS:
                case WRONG_USERNAME_OR_PASSWORD:
                    continue;
                case EXIT:
                case LOGIN_SUCCESS:
                    break;
            }
        }
        switch (result){
            case LOGIN_SUCCESS:
                break;
            case EXIT:
                System.out.println("Goodbye!!!");
                System.exit(0);
        }
    }




}
