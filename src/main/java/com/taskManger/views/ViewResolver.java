package com.taskManger.views;

import com.taskManger.entities.User;
import com.taskManger.views.results.AuthorizationViewResult;
import com.taskManger.views.results.ListOfTasksViewResult;
import com.taskManger.views.results.MainMenuViewResult;
import com.taskManger.views.results.TaskViewResult;

import java.util.InputMismatchException;

public class ViewResolver {
    AuthorizationView authorizationView;
    MainMenuView mainMenuView;
    ListOfTasksView listOfTasksView;
    TaskView taskView;
    User user;

    public ViewResolver(AuthorizationView authorizationView, MainMenuView mainMenuView, TaskView taskView, ListOfTasksView listOfTasksView) {
        this.authorizationView = authorizationView;
        this.mainMenuView = mainMenuView;
        this.taskView = taskView;
        this.listOfTasksView = listOfTasksView;
    }

    private void signIn(User user) {
        this.user = user;
        mainMenuView.setUser(this.user);
        taskView.setUser(this.user);
        listOfTasksView.setUser(this.user);
    }

    public void authorizationViewResponse(AuthorizationViewResult result) {

        while (result != AuthorizationViewResult.EXIT && result != AuthorizationViewResult.LOGIN_SUCCESS) {
            try {
                result = authorizationView.mainMenu();
                switch (result) {
                    case SIGN_IN:
                        result = authorizationView.signIn();
                        signIn(authorizationView.getUser());
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
            } catch (InputMismatchException e) {
                System.out.println("\n\nWrong input!!!\n");
                result = AuthorizationViewResult.WRONG_INPUT;
            }
        }


        switch (result) {
            case LOGIN_SUCCESS:
                mainMenuViewResponse(null);
                break;
            case EXIT:
                System.out.println("Goodbye!!!");
                return;
        }
    }

    public void mainMenuViewResponse(MainMenuViewResult result) {
        AuthorizationViewResult authorizationViewResult = null;
        while (result != MainMenuViewResult.EXIT && result != MainMenuViewResult.LOG_OUT) {
            try {
                result = mainMenuView.mainMenu();
                switch (result) {

                    case TASKS:
                        this.taskViewResponse(null);
                        return;
                    case LIST_OF_TASKS:
                        this.listOfTaskViewResponse(null);
                        return;
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
            } catch (InputMismatchException e) {
                System.out.println("\n\nWrong input!!!\n");
                result = MainMenuViewResult.WRONG_INPUT;
            }
        }
        if (result == MainMenuViewResult.EXIT) {
            authorizationViewResult = AuthorizationViewResult.EXIT;
        }
        this.authorizationViewResponse(authorizationViewResult);
    }

    public void taskViewResponse(TaskViewResult result) {
        while (result != TaskViewResult.BACK_TO_MAIN_MENU) {
            try {
                result = taskView.mainMenu();

                switch (result) {
                    case SHOW_TASKS:
                        taskView.showTasksToUser();
                        break;
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
            } catch (InputMismatchException e) {
                System.out.println("\n\nWrong input!!!\n");
                result = TaskViewResult.WRONG_INPUT;
            }
        }
        this.mainMenuViewResponse(null);

    }

    public void editTaskViewResponse(TaskViewResult result) {
        while (result != TaskViewResult.BACK_TO_MAIN_MENU && result != TaskViewResult.EDIT_SUCCESS) {
            try {
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
            } catch (InputMismatchException e) {
                System.out.println("\n\nWrong input!!!\n");
                result = TaskViewResult.WRONG_INPUT;
            }
        }
        this.mainMenuViewResponse(null);
    }

    public void createTaskViewResponse(TaskViewResult result) {
        while (result != TaskViewResult.BACK_TO_MAIN_MENU && result != TaskViewResult.CREATE_SUCCESS) {
            try {
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
            } catch (InputMismatchException e) {
                System.out.println("\n\nWrong input!!!\n");
                result = TaskViewResult.WRONG_INPUT;
            }
        }
        this.mainMenuViewResponse(null);
    }

    public void deleteTaskViewResponse(TaskViewResult result) {
        while (result != TaskViewResult.BACK_TO_MAIN_MENU && result != TaskViewResult.DELETE_SUCCESS) {
            try {
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
            } catch (InputMismatchException e) {
                System.out.println("\n\nWrong input!!!\n");
                result = TaskViewResult.WRONG_INPUT;
            }
        }
        this.mainMenuViewResponse(null);
    }


    // ListOfTask
    public void listOfTaskViewResponse(ListOfTasksViewResult result) {
        while (result != ListOfTasksViewResult.BACK_TO_MAIN_MENU) {
            try {
                result = listOfTasksView.mainMenu();

                switch (result) {
                    case SHOW_LISTS:
                        listOfTasksView.showListsToUser();
                        break;
                    case EDIT_LIST:
                        editListOfTaskViewResponse(null);
                        return;
                    case CREATE_LIST:
                        createListOfTaskViewResponse(null);
                        return;
                    case DELETE_LIST:
                        deleteListOfTaskViewResponse(null);
                        return;
                    case WRONG_INPUT:
                        break;
                    case BACK_TO_MAIN_MENU:
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("\n\nWrong input!!!\n");
                result = ListOfTasksViewResult.WRONG_INPUT;
            }
        }
        this.mainMenuViewResponse(null);

    }

    public void editListOfTaskViewResponse(ListOfTasksViewResult result) {

        while (result != ListOfTasksViewResult.BACK_TO_MAIN_MENU && result != ListOfTasksViewResult.EDIT_SUCCESS && result != ListOfTasksViewResult.ADD_SUCCESS) {
            try {
                result = listOfTasksView.editListOfTask();
                switch (result) {
                    case EDIT_LIST_NAME:
                        result = listOfTasksView.editListName();
                        break;
                    case ADD_TASK:
//                    result = taskView.editTaskDescription();
                        result = listOfTasksView.addTaskForUser();
                        break;
                    case EDIT_TASK_FOR_USER:
//                    result = taskView.editTaskAlertTime();
                        editTaskForUserViewResponse(null);
                        return;
                    case WRONG_INPUT:
                        break;
                    case WRONG_INDEX:
                        break;
                    case EDIT_SUCCESS:
                        break;
                    case BACK_TO_LIST_VIEW:
                        this.listOfTaskViewResponse(null);
                        return;
                    case BACK_TO_MAIN_MENU:
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("\n\nWrong input!!!\n");
                result = ListOfTasksViewResult.WRONG_INPUT;
            }
        }
        this.mainMenuViewResponse(null);
    }


    public void editTaskForUserViewResponse(ListOfTasksViewResult result) {
        while (result != ListOfTasksViewResult.BACK_TO_MAIN_MENU && result != ListOfTasksViewResult.EDIT_SUCCESS) {
            try {
                result = listOfTasksView.editTaskForUserView();
                switch (result) {
                    case EDIT_TASK_FOR_USER_NAME:
                        result = listOfTasksView.editTaskForUserName();
                        break;
                    case ADD_USER_TO_TASK:
//                    result = taskView.editTaskDescription();
                        result = listOfTasksView.addUserToTask();
                        break;
                    case DELETE_TASK:
                        deleteTaskForUserViewResponse(null);
                        return;
                    case WRONG_INPUT:
                        break;
                    case EDIT_SUCCESS:
                        break;
                    case BACK_TO_LIST_VIEW:
                        this.listOfTaskViewResponse(null);
                        return;
                    case BACK_TO_MAIN_MENU:
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("\n\nWrong input!!!\n");
                result = ListOfTasksViewResult.WRONG_INPUT;
            }
        }
        this.mainMenuViewResponse(null);
    }

    public void deleteTaskForUserViewResponse(ListOfTasksViewResult result) {
        while (result != ListOfTasksViewResult.BACK_TO_MAIN_MENU && result != ListOfTasksViewResult.DELETE_SUCCESS) {
            try {
                result = listOfTasksView.editTaskForUserView();
                switch (result) {
                    case DELETE_SUCCESS:
                        break;
                    case WRONG_INPUT:
                        break;
                    case BACK_TO_LIST_VIEW:
                        this.listOfTaskViewResponse(null);
                        return;
                    case BACK_TO_MAIN_MENU:
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("\n\nWrong input!!!\n");
                result = ListOfTasksViewResult.WRONG_INPUT;
            }
        }
        this.mainMenuViewResponse(null);
    }


    public void createListOfTaskViewResponse(ListOfTasksViewResult result) {
        while (result != ListOfTasksViewResult.BACK_TO_MAIN_MENU && result != ListOfTasksViewResult.CREATE_SUCCESS) {
            try {
                result = listOfTasksView.createList();
                switch (result) {
                    case WRONG_INPUT:
                        break;
                    case CREATE_SUCCESS:
                        break;
                    case BACK_TO_LIST_VIEW:
                        this.listOfTaskViewResponse(null);
                        return;
                    case BACK_TO_MAIN_MENU:
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("\n\nWrong input!!!\n");
                result = ListOfTasksViewResult.WRONG_INPUT;
            }
        }
        this.mainMenuViewResponse(null);
    }

    public void deleteListOfTaskViewResponse(ListOfTasksViewResult result) {
        while (result != ListOfTasksViewResult.BACK_TO_MAIN_MENU && result != ListOfTasksViewResult.DELETE_SUCCESS) {
            try {
                result = listOfTasksView.deleteList();
                switch (result) {
                    case WRONG_INPUT:
                        break;
                    case WRONG_INDEX:
                        break;
                    case DELETE_SUCCESS:
                        break;
                    case BACK_TO_LIST_VIEW:
                        this.listOfTaskViewResponse(null);
                        return;
                    case BACK_TO_MAIN_MENU:
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("\n\nWrong input!!!\n");
                result = ListOfTasksViewResult.WRONG_INPUT;
            }
        }
        this.mainMenuViewResponse(null);
    }
}
