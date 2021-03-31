package com.taskManger.views;

import com.taskManger.views.results.AuthorizationViewResult;
import com.taskManger.views.results.MainMenuViewResult;
import com.taskManger.views.results.TaskViewResult;

public class ViewResolver {
    AuthorizationView authorizationView;
    MainMenuView mainMenuView;
    TaskView taskView;

    public void authorizationViewResponse(AuthorizationViewResult result) {

        while (result != AuthorizationViewResult.EXIT && result != AuthorizationViewResult.LOGIN_SUCCESS) {
            result = authorizationView.mainMenu();
            switch (result) {
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


        switch (result) {
            case LOGIN_SUCCESS:
                break;
            case EXIT:
                System.out.println("Goodbye!!!");
                System.exit(0);
        }
    }

    public void mainMenuViewResponse(MainMenuViewResult result) {
        AuthorizationViewResult authorizationViewResult = null;
        while (result != MainMenuViewResult.EXIT && result != MainMenuViewResult.LOG_OUT) {
            result = mainMenuView.mainMenu();
            switch (result) {
                case TASKS:
                    break;
                case LIST_OF_TASKS:
                    break;
                case FIND_TASK:
                    break;
                case WRONG_INPUT:
                    break;
                case LOG_OUT:
                    break;
                case EXIT:
                    break;
            }
        }
        if(result == MainMenuViewResult.EXIT){
            authorizationViewResult = AuthorizationViewResult.EXIT;
        }
        this.authorizationViewResponse(authorizationViewResult);
    }

    public void taskViewResponse(TaskViewResult result) {
        while (result != TaskViewResult.BACK_TO_MAIN_MENU) {
            result = taskView.mainMenu();
            switch (result) {
                case EDIT_TASK:
                    break;
                case CREATE_TASK:
                    break;
                case DELETE_TASK:
                    break;
                case WRONG_INPUT:
                    break;
                case BACK_TO_MAIN_MENU:
                    break;
            }
        }
        this.mainMenuViewResponse(null);

    }

    public void editTaskViewResponse(TaskViewResult result){
        while (result != TaskViewResult.BACK_TO_MAIN_MENU) {
            result = taskView.editTask();
            switch (result) {
                case EDIT_NAME:
                    this.editNameViewResponse();
                case EDIT_DESCRIPTION:
//                    result = taskView.editTaskDescription();
                case EDIT_ALERT_TIME:
//                    result = taskView.editTaskAlertTime();
                case WRONG_INPUT:
                    break;
                case BACK_TO_MAIN_MENU:
                    break;
            }
        }
        this.mainMenuViewResponse(null);
    }

    private void editNameViewResponse(){
        TaskViewResult result = null;
        while (result != TaskViewResult.BACK_TO_MAIN_MENU && result != TaskViewResult.EDIT_SUCCESS) {
            result = taskView.editTaskName();
            switch (result) {
                case WRONG_INPUT:
                    break;
                case BACK_TO_MAIN_MENU:
                    break;
                case EDIT_SUCCESS:
                    break;
            }
        }

        this.mainMenuViewResponse(null);
    }
}
