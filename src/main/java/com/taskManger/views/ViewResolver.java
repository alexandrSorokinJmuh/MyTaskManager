package com.taskManger.views;

import com.taskManger.entities.User;
import com.taskManger.views.results.AuthorizationViewResult;
import com.taskManger.views.results.MainMenuViewResult;
import com.taskManger.views.results.TaskViewResult;

public class ViewResolver {
    AuthorizationView authorizationView;
    MainMenuView mainMenuView;
    TaskView taskView;
    User user;

    public ViewResolver(AuthorizationView authorizationView, MainMenuView mainMenuView, TaskView taskView) {
        this.authorizationView = authorizationView;
        this.mainMenuView = mainMenuView;
        this.taskView = taskView;
    }

    public void authorizationViewResponse(AuthorizationViewResult result) {

        while (result != AuthorizationViewResult.EXIT && result != AuthorizationViewResult.LOGIN_SUCCESS) {
            result = authorizationView.mainMenu();
            switch (result) {
                case SIGN_IN:
                    result = authorizationView.signIn();
                    this.user = authorizationView.getUser();
                    mainMenuView.setUser(this.user);
                    taskView.setUser(this.user);
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
                mainMenuViewResponse(null);
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
                    this.taskViewResponse(null);
                    break;
                case LIST_OF_TASKS:
                    break;
                case FIND_TASK:
                    break;
                case WRONG_INPUT:
                    break;
                case LOG_OUT:
                    this.user = null;
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
                    editTaskViewResponse(null);
                    return;
                case CREATE_TASK:
                    createTaskViewResponse(null);
                    return;
                case DELETE_TASK:
                    deleteTaskViewResponse(null);
                    return;
                case WRONG_INPUT:
                    break;
                case BACK_TO_MAIN_MENU:
                    break;
            }
        }
        this.mainMenuViewResponse(null);

    }

    public void editTaskViewResponse(TaskViewResult result){
        while (result != TaskViewResult.BACK_TO_MAIN_MENU && result != TaskViewResult.EDIT_SUCCESS) {
            result = taskView.editTask();
            switch (result) {
                case EDIT_NAME:
                    result = taskView.editTaskName();
                    break;
                case EDIT_DESCRIPTION:
//                    result = taskView.editTaskDescription();
                    result = taskView.editTaskDescription();
                    break;
                case EDIT_ALERT_TIME:
//                    result = taskView.editTaskAlertTime();
                    result = taskView.editTaskAlertTime();
                    break;
                case WRONG_INPUT:
                    break;
                case EDIT_SUCCESS:
                    break;
                case BACK_TO_TASK_VIEW:
                    this.taskViewResponse(null);
                    return;
                case BACK_TO_MAIN_MENU:
                    break;
            }
        }
        this.mainMenuViewResponse(null);
    }

    public void createTaskViewResponse(TaskViewResult result){
        while (result != TaskViewResult.BACK_TO_MAIN_MENU && result != TaskViewResult.CREATE_SUCCESS) {
            result = taskView.createTask();
            switch (result) {
                case WRONG_INPUT:
                    break;
                case CREATE_SUCCESS:
                    break;
                case BACK_TO_TASK_VIEW:
                    this.taskViewResponse(null);
                    return;
                case BACK_TO_MAIN_MENU:
                    break;
            }
        }
        this.mainMenuViewResponse(null);
    }

    public void deleteTaskViewResponse(TaskViewResult result){
        while (result != TaskViewResult.BACK_TO_MAIN_MENU && result != TaskViewResult.DELETE_SUCCESS) {
            result = taskView.deleteTask();
            switch (result) {
                case WRONG_INPUT:
                    break;
                case WRONG_NUMBER_OF_TASK:
                    break;
                case DELETE_SUCCESS:
                    break;
                case BACK_TO_TASK_VIEW:
                    this.taskViewResponse(null);
                    return;
                case BACK_TO_MAIN_MENU:
                    break;
            }
        }
        this.mainMenuViewResponse(null);
    }
}
