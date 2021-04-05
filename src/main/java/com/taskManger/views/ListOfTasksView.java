package com.taskManger.views;

import com.taskManger.controllers.ListOfTasksController;
import com.taskManger.controllers.TaskController;
import com.taskManger.controllers.UserController;
import com.taskManger.entities.ListOfTasks;
import com.taskManger.entities.TaskForUser;
import com.taskManger.entities.Tasks;
import com.taskManger.entities.User;
import com.taskManger.exception.EntityNotFoundException;
import com.taskManger.exception.UUIDIsNotUniqueException;
import com.taskManger.views.results.ListOfTasksViewResult;
import com.taskManger.views.results.TaskViewResult;

import java.util.List;
import java.util.Scanner;

public class ListOfTasksView {
    private final int mainMenuActionCount = 5;
    private final int editListActionCount = 5;
    private final int editTaskActionCount = 5;
    private final int deleteTaskActionCount = 4;
    private UserController userController;
    private TaskController taskController;
    private ListOfTasksController listOfTasksController;
    private User user;
    private ListOfTasks currentListOfTask;
    private TaskForUser currentTaskForUser;

    public ListOfTasksView(UserController userController, TaskController taskController, ListOfTasksController listOfTasksController) {
        this.userController = userController;
        this.taskController = taskController;
        this.listOfTasksController = listOfTasksController;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ListOfTasksViewResult mainMenu() {
        System.out.println("\n\nChoose action to do:");
        System.out.println("1. Show lists");
        System.out.println("2. Edit list");
        System.out.println("3. Create list");
        System.out.println("4. Delete list");
        System.out.println("5. Back to main menu");

        Scanner in = new Scanner(System.in);
        System.out.print("Input a number: ");
        int num = in.nextInt();

        if (num >= 1 && num <= mainMenuActionCount) {
            switch (num) {
                case 1:
                    // EDIT_TASK
                    return ListOfTasksViewResult.SHOW_LISTS;
                case 2:
                    // EDIT_TASK
                    return ListOfTasksViewResult.EDIT_LIST;
                case 3:
                    // CREATE_TASK
                    return ListOfTasksViewResult.CREATE_LIST;
                case 4:
                    // DELETE_TASK
                    return ListOfTasksViewResult.DELETE_LIST;
                case 5:
                    // Exit
                    return ListOfTasksViewResult.BACK_TO_MAIN_MENU;
            }
        } else {
            System.out.println("\n\nWrong input!!!\n");
            return ListOfTasksViewResult.WRONG_INPUT;
        }
        return ListOfTasksViewResult.BACK_TO_MAIN_MENU;
    }

    private void showListOfTaskList(List<ListOfTasks> listOfTasks) {
        for (int i = 0; i < listOfTasks.size(); i++) {
            System.out.println(String.format("%d: List - %s", i + 1, listOfTasks.get(i).getName()));
        }
    }

    private void showTasksForUser(List<TaskForUser> taskForUsers) {
        for (int i = 0; i < taskForUsers.size(); i++) {
            System.out.println(String.format("%d: List - %s", i + 1, taskForUsers.get(i).getName()));
        }
    }

    private void showUsers(List<User> users) {
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            System.out.println(String.format("%d: User - %s;\tFirstName: %s,\tLastName: %s", i + 1, user.getUsername(), user.getFirstName(), user.getLastName()));
        }
    }

    private void showTasks(List<Tasks> tasksList) {
        for (int i = 0; i < tasksList.size(); i++) {
            Tasks task = tasksList.get(i);
            try {
                User user = userController.getUserByUuid(task.getCreatorUuid());
                System.out.println(String.format("%d: Task - %s by %s", i + 1, task.getName(), user.getUsername()));
            } catch (UUIDIsNotUniqueException | EntityNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
    public void showListsToUser() {
        List<ListOfTasks> listOfTasks = listOfTasksController.getListsCreatedByUser(user);
        showListOfTaskList(listOfTasks);
    }


    public ListOfTasksViewResult editListOfTask() {

        List<ListOfTasks> listOfTasks = listOfTasksController.getListsCreatedByUser(user);
        showListOfTaskList(listOfTasks);
        if (listOfTasks.size() == 0){
            System.out.println("Have not lists to edit");
            return ListOfTasksViewResult.LIST_IS_EMPTY;
        }
        System.out.println("\n\nChoose list index to edit");
        Scanner in = new Scanner(System.in);
        System.out.print("Input a number: ");
        int num = in.nextInt();
        if (num >= 1 && num <= listOfTasks.size()) {
            currentListOfTask = listOfTasks.get(num - 1);

            System.out.println("Choose action");
            System.out.println("1. Edit name");
            System.out.println("2. Add task for user");
            System.out.println("3. Edit task for user");
            System.out.println("4. Back to lists actions");
            System.out.println("5. Back to main menu");
            System.out.print("Input a number: ");

            num = in.nextInt();
            if (num >= 1 && num <= editListActionCount) {
                switch (num) {
                    case 1:
                        return ListOfTasksViewResult.EDIT_LIST_NAME;
                    case 2:
                        return ListOfTasksViewResult.ADD_TASK;
                    case 3:
                        return ListOfTasksViewResult.EDIT_TASK_FOR_USER;
                    case 4:
                        currentListOfTask = null;
                        return ListOfTasksViewResult.BACK_TO_LIST_VIEW;
                    case 5:
                        currentListOfTask = null;
                        return ListOfTasksViewResult.BACK_TO_MAIN_MENU;
                }
            } else {
                System.out.println("\n\nWrong input!!!\n");
                return ListOfTasksViewResult.WRONG_INPUT;
            }
        } else {
            System.out.println("\n\nWrong number of list!!!\n");
            return ListOfTasksViewResult.WRONG_INDEX;
        }
        currentListOfTask = null;
        return ListOfTasksViewResult.BACK_TO_LIST_VIEW;

    }

    public ListOfTasksViewResult addTaskForUser(){
        List<Tasks> tasksList = listOfTasksController.getTasksNotInList(currentListOfTask);
        showTasks(tasksList);
        if (tasksList.size() == 0){
            System.out.println("Have not task to edit");
            return ListOfTasksViewResult.LIST_IS_EMPTY;
        }
        System.out.println("\n\nChoose task index to edit");
        Scanner sc = new Scanner(System.in);
        System.out.print("Input a number: ");
        int num = sc.nextInt();
        if (num >= 1 && num <= tasksList.size()) {
            Tasks task = tasksList.get(num - 1);

            List<User> userList = userController.getAllUsers();
            showUsers(userList);
            System.out.println("\n\nChoose user index to edit");
            System.out.print("Input a number: ");
            num = sc.nextInt();
            if (num >= 1 && num <= userList.size()) {
                User user = userList.get(num - 1);

                System.out.println("Input task to user name: ");
                sc = new Scanner(System.in);
                String name = sc.nextLine();

                try {
                    listOfTasksController.addTaskToList(user, currentListOfTask, task, name);
                } catch (UUIDIsNotUniqueException | EntityNotFoundException e) {
                    e.printStackTrace();
                    currentTaskForUser = null;
                    return ListOfTasksViewResult.BACK_TO_MAIN_MENU;
                }
                System.out.println("Task added successfully");
                return ListOfTasksViewResult.ADD_SUCCESS;
            } else {
                System.out.println("\n\nWrong number of user!!!\n");
                return ListOfTasksViewResult.WRONG_INDEX;
            }
        } else {
            System.out.println("\n\nWrong number of task!!!\n");
            return ListOfTasksViewResult.WRONG_INDEX;
        }
    }

    public ListOfTasksViewResult editListName() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Input new list name: ");
        String nameNew = sc.nextLine();
        try {
            listOfTasksController.changeName(currentListOfTask, nameNew);
        } catch (UUIDIsNotUniqueException | EntityNotFoundException e) {
            e.printStackTrace();
            return ListOfTasksViewResult.BACK_TO_MAIN_MENU;
        }
        System.out.println("Edit success");
        return ListOfTasksViewResult.EDIT_SUCCESS;
    }

    public ListOfTasksViewResult editTaskForUserName() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Input new task for user name: ");
        String nameNew = sc.nextLine();
        try {
            listOfTasksController.changeTaskForUserName(currentTaskForUser, nameNew);
        } catch (UUIDIsNotUniqueException | EntityNotFoundException e) {
            e.printStackTrace();
            return ListOfTasksViewResult.BACK_TO_MAIN_MENU;
        }
        System.out.println("Edit success");
        return ListOfTasksViewResult.EDIT_SUCCESS;
    }

    public ListOfTasksViewResult editTaskForUserView() {


        List<TaskForUser> taskForUsers = listOfTasksController.getAllTasksByList(currentListOfTask);

        showTasksForUser(taskForUsers);
        if (taskForUsers.size() == 0){
            System.out.println("Have not task for user to edit");
            return ListOfTasksViewResult.LIST_IS_EMPTY;
        }
        System.out.println("\n\nChoose task index to edit");
        Scanner in = new Scanner(System.in);
        System.out.print("Input a number: ");
        int num = in.nextInt();

        if (num >= 1 && num <= taskForUsers.size()) {
            currentTaskForUser = taskForUsers.get(num - 1);

            System.out.println("Choose action");
            System.out.println("1. Edit name");
            System.out.println("2. Add user to task");
            System.out.println("3. Delete task");
            System.out.println("4. Back to lists actions");
            System.out.println("5. Back to main menu");
            System.out.print("Input a number: ");

            num = in.nextInt();
            if (num >= 1 && num <= editTaskActionCount) {
                switch (num) {
                    case 1:
                        return ListOfTasksViewResult.EDIT_TASK_FOR_USER_NAME;
                    case 2:
                        return ListOfTasksViewResult.ADD_USER_TO_TASK;
                    case 3:
                        return ListOfTasksViewResult.DELETE_TASK;
                    case 4:
                        currentTaskForUser = null;
                        return ListOfTasksViewResult.BACK_TO_LIST_VIEW;
                    case 5:
                        currentTaskForUser = null;
                        currentListOfTask = null;
                        return ListOfTasksViewResult.BACK_TO_MAIN_MENU;
                }
            } else {
                System.out.println("\n\nWrong input!!!\n");
                return ListOfTasksViewResult.WRONG_INPUT;
            }
        } else {
            System.out.println("\n\nWrong number of task!!!\n");
            return ListOfTasksViewResult.WRONG_INDEX;
        }
        currentListOfTask = null;
        return ListOfTasksViewResult.BACK_TO_LIST_VIEW;
    }

    public ListOfTasksViewResult addUserToTask(){
        List<User> userList = listOfTasksController.getUsersNotInTask(currentTaskForUser);
        showUsers(userList);
        if (userList.size() == 0){
            System.out.println("Have no user to add to task");
            return ListOfTasksViewResult.LIST_IS_EMPTY;
        }
        System.out.println("\n\nChoose user index to add");
        Scanner sc = new Scanner(System.in);
        int num = sc.nextInt();
        if (num >= 1 && num <= userList.size()) {
            try {
                listOfTasksController.addUserToTask(currentTaskForUser, user);
            } catch (UUIDIsNotUniqueException e) {
                e.printStackTrace();
                currentTaskForUser = null;
                currentListOfTask = null;
                return ListOfTasksViewResult.BACK_TO_MAIN_MENU;
            }
        }else {
            System.out.println("\n\nWrong number of user!!!\n");
            return ListOfTasksViewResult.WRONG_INDEX;
        }
        return ListOfTasksViewResult.ADD_SUCCESS;
    }

    public ListOfTasksViewResult deleteTaskView() {
        System.out.println("Choose action");
        System.out.println("1. Remove user from task");
        System.out.println("2. Delete task");
        System.out.println("3. Back to lists actions");
        System.out.println("4. Back to main menu");
        System.out.print("Input a number: ");
        Scanner sc = new Scanner(System.in);
        int num = sc.nextInt();
        if (num >= 1 && num <= deleteTaskActionCount) {

            try {
                switch (num) {
                    case 1:
                        listOfTasksController.deleteOnlyTaskForUser(currentTaskForUser);
                        currentTaskForUser = null;
                        return ListOfTasksViewResult.DELETE_SUCCESS;
                    case 2:
                        listOfTasksController.deleteTask(currentTaskForUser);
                        return ListOfTasksViewResult.DELETE_SUCCESS;
                    case 3:
                        currentTaskForUser = null;
                        return ListOfTasksViewResult.BACK_TO_LIST_VIEW;
                    case 4:
                        currentTaskForUser = null;
                        currentListOfTask = null;
                        return ListOfTasksViewResult.BACK_TO_MAIN_MENU;

                }
            } catch (UUIDIsNotUniqueException | EntityNotFoundException e) {
                e.printStackTrace();
                currentTaskForUser = null;
                currentListOfTask = null;
                return ListOfTasksViewResult.BACK_TO_MAIN_MENU;
            }

        } else {
            System.out.println("\n\nWrong number of list!!!\n");
            return ListOfTasksViewResult.WRONG_INDEX;
        }
        currentTaskForUser = null;
        return ListOfTasksViewResult.BACK_TO_LIST_VIEW;
    }


    public ListOfTasksViewResult createList() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Input new list name: ");
        String nameNew = sc.nextLine();
        try {
            listOfTasksController.createNewListOfTasks(user, nameNew);
        } catch (UUIDIsNotUniqueException e) {
            e.printStackTrace();
            return ListOfTasksViewResult.BACK_TO_MAIN_MENU;
        }
        return ListOfTasksViewResult.CREATE_SUCCESS;
    }

    public ListOfTasksViewResult deleteList() {
        Scanner sc = new Scanner(System.in);
        List<ListOfTasks> listOfTasks = listOfTasksController.getListsCreatedByUser(user);
        showListOfTaskList(listOfTasks);
        if (listOfTasks.size() == 0){
            System.out.println("Have no lists to delete");
            return ListOfTasksViewResult.LIST_IS_EMPTY;
        }
        System.out.println("Input list index to delete: ");
        int num = sc.nextInt();
        if (num >= 1 && num <= listOfTasks.size()) {
            try {
                listOfTasksController.deleteList(listOfTasks.get(num - 1));
            } catch (UUIDIsNotUniqueException | EntityNotFoundException e) {
                e.printStackTrace();
                return ListOfTasksViewResult.BACK_TO_MAIN_MENU;
            }
        } else {
            System.out.println("\n\nWrong number of list!!!\n");
            return ListOfTasksViewResult.WRONG_INDEX;
        }
        return ListOfTasksViewResult.DELETE_SUCCESS;
    }
}
