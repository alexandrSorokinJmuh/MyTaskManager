package com.taskManger.views;

import com.taskManger.controllers.TaskController;
import com.taskManger.controllers.UserController;
import com.taskManger.entities.Tasks;
import com.taskManger.entities.User;
import com.taskManger.exception.EntityNotFoundException;
import com.taskManger.exception.UUIDIsNotUniqueException;
import com.taskManger.views.results.TaskViewResult;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class TaskView {
    private final int mainMenuActionCount = 5;
    private final int editViewActionCount = 6;
    private UserController userController;
    private TaskController taskController;
    private User user;
    private Tasks currentTask;

    public void setUser(User user) {
        this.user = user;
    }

    public void notifyObservers(){
        taskController.notifyObserver(user);
    }

    public TaskView(UserController userController, TaskController taskController, User user, Tasks currentTask) {
        this.userController = userController;
        this.taskController = taskController;
        this.user = user;
        this.currentTask = currentTask;
        taskController.getFromWatcherObservers();
    }

    public TaskView(UserController userController, TaskController taskController, User user) {
        this.userController = userController;
        this.taskController = taskController;
        this.user = user;
    }

    public TaskViewResult mainMenu() {
        System.out.println("\n\nChoose action to do:");
        System.out.println("1. Show tasks");
        System.out.println("2. Edit task");
        System.out.println("3. Create task");
        System.out.println("4. Delete task");
        System.out.println("5. Exit");

        Scanner in = new Scanner(System.in);
        System.out.print("Input a number: ");
        int num = in.nextInt();

        if (num >= 1 && num <= mainMenuActionCount) {
            switch (num) {
                case 1:
                    // Show tasks
                    return TaskViewResult.SHOW_TASKS;
                case 2:
                    // EDIT_TASK
                    return TaskViewResult.EDIT_TASK;
                case 3:
                    // CREATE_TASK
                    return TaskViewResult.CREATE_TASK;
                case 4:
                    // DELETE_TASK
                    return TaskViewResult.DELETE_TASK;
                case 5:
                    // Exit
                    return TaskViewResult.BACK_TO_MAIN_MENU;
            }
        } else {
            System.out.println("\n\nWrong input!!!\n");
            return TaskViewResult.WRONG_INPUT;
        }
        return TaskViewResult.BACK_TO_MAIN_MENU;
    }

    private void showTaskList(List<Tasks> tasksList) {
        for (int i = 0; i < tasksList.size(); i++) {
            System.out.println(String.format("%d: Task - %s", i + 1, tasksList.get(i).getName()));
        }
    }

    private void showUserList(List<User> userList) {
        for (int i = 0; i < userList.size(); i++) {
            User user = userList.get(i);
            System.out.println(String.format("%d: User - username: %s\tfirstName: %s\tlastName: %s", i + 1, user.getUsername(), user.getFirstName(), user.getLastName()));
        }
    }

    public void showTasksToUser() {
        List<Tasks> tasksList = taskController.getAllTasksByUser(user);
        showTaskList(tasksList);
    }

    public TaskViewResult editTask() {
        List<Tasks> tasksList = taskController.getAllTasksByUser(user);

        showTaskList(tasksList);
        if (tasksList.size() == 0){
            System.out.println("Have not tasks to edit");
            return TaskViewResult.LIST_IS_EMPTY;
        }
        Scanner in = new Scanner(System.in);
        System.out.println("\n\nChoose task index to edit");
        int num = in.nextInt();
        if (num >= 1 && num <= tasksList.size()) {
            currentTask = tasksList.get(num - 1);

            System.out.println("Choose action");
            System.out.println("1. Edit task name");
            System.out.println("2. Edit task description");
            System.out.println("3. Edit task alert time");
            System.out.println("4. Add task watcher");
            System.out.println("5. Back to task actions");
            System.out.println("6. Back to main menu");
            System.out.print("Input a number: ");

            num = in.nextInt();
            if (num >= 1 && num <= editViewActionCount) {
                switch (num) {
                    case 1:
                        return TaskViewResult.EDIT_NAME;
                    case 2:
                        return TaskViewResult.EDIT_DESCRIPTION;
                    case 3:
                        return TaskViewResult.EDIT_ALERT_TIME;
                    case 4:
                        return TaskViewResult.ADD_WATCHER_TO_TASK;
                    case 5:
                        currentTask = null;
                        return TaskViewResult.BACK_TO_TASK_VIEW;
                    case 6:
                        currentTask = null;
                        return TaskViewResult.BACK_TO_MAIN_MENU;
                }
            } else {
                System.out.println("\n\nWrong input!!!\n");
                return TaskViewResult.WRONG_INPUT;
            }
        } else {
            System.out.println("\n\nWrong number of task!!!\n");
            return TaskViewResult.WRONG_INDEX;
        }
        currentTask = null;
        return TaskViewResult.BACK_TO_TASK_VIEW;

    }

    public TaskViewResult addWatcherToTask() {
        List<User> users = taskController.getUsersNotWatchingTask(currentTask);
        showUserList(users);
        if (users.size() == 0){
            System.out.println("Have not anymore users to add");
            return TaskViewResult.LIST_IS_EMPTY;
        }
        Scanner sc = new Scanner(System.in);


        System.out.println("Input user index: ");
        int num = sc.nextInt();
        if (num >= 1 && num <= users.size()){
            User user = users.get(num - 1);
            try {
                taskController.addWatcherForTask(user, currentTask);
            } catch (UUIDIsNotUniqueException e) {
                e.printStackTrace();
            }
            return TaskViewResult.EDIT_SUCCESS;
        }else {
            System.out.println("\n\nWrong index of user!!!\n");
            return TaskViewResult.WRONG_INDEX;
        }
    }

    public TaskViewResult editTaskName() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Input new task name: ");
        String nameNew = sc.nextLine();
        try {
            taskController.changeName(currentTask, nameNew);
        } catch (UUIDIsNotUniqueException | EntityNotFoundException e) {
            e.printStackTrace();
            return TaskViewResult.BACK_TO_MAIN_MENU;
        }
        System.out.println("Edit success");
        return TaskViewResult.EDIT_SUCCESS;
    }

    public TaskViewResult editTaskDescription() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Input new task description: ");
        String descriptionNew = sc.nextLine();
        try {
            taskController.changeAlertTime(currentTask, descriptionNew);
        } catch (UUIDIsNotUniqueException | EntityNotFoundException e) {
            e.printStackTrace();
            return TaskViewResult.BACK_TO_MAIN_MENU;
        }
        return TaskViewResult.EDIT_SUCCESS;
    }

    public TaskViewResult editTaskAlertTime() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Input new task alert time: ");
        String date = sc.nextLine();
        try {
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date dateNew = dateFormat.parse(date);
            taskController.changeAlertTime(currentTask, dateNew);
        } catch (UUIDIsNotUniqueException | EntityNotFoundException e) {
            e.printStackTrace();
            return TaskViewResult.BACK_TO_MAIN_MENU;
        } catch (ParseException e) {
            System.out.println("\n\nWrong date format\n");
            return TaskViewResult.WRONG_INPUT;
        }
        return TaskViewResult.EDIT_SUCCESS;
    }

    public TaskViewResult createTask() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Input new task name: ");
        String nameNew = sc.nextLine();

        System.out.println("Input new task description: ");
        String descriptionNew = sc.nextLine();

        System.out.println("Input new task alert time: ");
        String date = sc.nextLine();
        Date dateNew;
        try {
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            dateNew = dateFormat.parse(date);
        } catch (ParseException e) {
            System.out.println("\n\nWrong date format\n");
            return TaskViewResult.WRONG_INPUT;
        }
        try {
            taskController.createNewTask(nameNew, user.getUuid(), descriptionNew, dateNew);
        } catch (UUIDIsNotUniqueException e) {
            e.printStackTrace();
            return TaskViewResult.BACK_TO_MAIN_MENU;
        }
        return TaskViewResult.CREATE_SUCCESS;
    }

    public TaskViewResult deleteTask() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Input task index to delete: ");
        List<Tasks> tasksList = taskController.getAllTasksByUser(user);
        showTaskList(tasksList);
        if (tasksList.size() == 0){
            System.out.println("Have not tasks to delete");
            return TaskViewResult.LIST_IS_EMPTY;
        }
        System.out.print("Input a number: ");
        int num = sc.nextInt();
        if (num >= 1 && num <= tasksList.size()) {
            try {
                taskController.deleteTask(tasksList.get(num - 1));
            } catch (UUIDIsNotUniqueException | EntityNotFoundException e) {
                e.printStackTrace();
                return TaskViewResult.BACK_TO_MAIN_MENU;
            }
        } else {
            System.out.println("\n\nWrong number of task!!!\n");
            return TaskViewResult.WRONG_INDEX;
        }
        return TaskViewResult.DELETE_SUCCESS;
    }
}
