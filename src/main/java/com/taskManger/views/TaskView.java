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
    private final int mainMenuActionCount = 4;
    private final int editViewActionCount = 4;
    private UserController userController;
    private TaskController taskController;
    private User user;
    private Tasks currentTask;

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
                    return TaskViewResult.BACK_TO_MAIN_MENU;
            }
        } else {
            System.out.println("\n\nWrong input!!!\n");
            return TaskViewResult.WRONG_INPUT;
        }
        return TaskViewResult.BACK_TO_MAIN_MENU;
    }

    public TaskViewResult editTask(){
        List<Tasks> tasksList = taskController.getAllTaskByUser(user);
        System.out.println("Choose task to edit");
        for (int i = 0; i < tasksList.size(); i++){
            System.out.println(String.format("%d: Task - %s", i+1, tasksList.get(i).getName()));
        }
        Scanner in = new Scanner(System.in);
        System.out.print("Input a number: ");
        int num = in.nextInt();
        if (num >= 1 && num <= tasksList.size()){
            currentTask = tasksList.get(num - 1);

            System.out.println("Choose action");
            System.out.println("1. Edit task name");
            System.out.println("2. Edit task description");
            System.out.println("3. Edit task alert time");
            System.out.println("4. Back to task actions");
            System.out.print("Input a number: ");

            num = in.nextInt();
            if(num >= 1 && num <= editViewActionCount){
                switch (num){
                    case 1:
                        return TaskViewResult.EDIT_NAME;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        currentTask = null;
                        return TaskViewResult.BACK_TO_TASK_VIEW;
                }
            }else {
                System.out.println("\n\nWrong input!!!\n");
                return TaskViewResult.WRONG_INPUT;
            }
        }else{
            System.out.println("\n\nWrong number of task!!!\n");
            return TaskViewResult.WRONG_NUMBER_OF_TASK;
        }
        currentTask = null;
        return TaskViewResult.BACK_TO_TASK_VIEW;

    }

    public TaskViewResult editTaskName(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Input new task name: ");
        String nameNew = sc.next();
        try {
            taskController.changeName(currentTask, nameNew);
        } catch (UUIDIsNotUniqueException | EntityNotFoundException e) {
            e.printStackTrace();
            return TaskViewResult.BACK_TO_MAIN_MENU;
        }
        System.out.println("Edit success");
        return TaskViewResult.EDIT_SUCCESS;
    }

    public TaskViewResult editTaskDescription(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Input new task description: ");
        String descriptionNew = sc.next();
        try {
            taskController.changeAlertTime(currentTask, descriptionNew);
        } catch (UUIDIsNotUniqueException | EntityNotFoundException e) {
            e.printStackTrace();
            return TaskViewResult.BACK_TO_MAIN_MENU;
        }
        return TaskViewResult.EDIT_SUCCESS;
    }

    public TaskViewResult editTaskAlertTime(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Input new task description: ");
        String date = sc.next();
        try {
            DateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy");
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
}
