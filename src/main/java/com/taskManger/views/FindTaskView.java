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
import com.taskManger.views.results.FindTaskViewResult;
import lombok.Getter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class FindTaskView {

    private final int mainMenuActionCount = 7;
    private final int filterByNameActionCount = 5;
    private final int filterByAlertTimeActionCount = 5;
    private final int filterByUserActionCount = 7;
    private final int filterByListActionCount = 5;
    private UserController userController;
    private TaskController taskController;
    private ListOfTasksController listOfTasksController;
    private User user;
    private FilterTask filterTask = new FilterTask();

    @Getter
    private class FilterTask{
        private String namePattern = "";
        private boolean fullNameMatch = false;
        private boolean isNameFilter = false;

        public void setNamePattern(String namePattern) {
            if (!isNameFilter)
                isNameFilter = true;
            this.namePattern = namePattern;
        }

        public void clearNameFilter(){
            isNameFilter = false;
            this.namePattern = "";
        }
        public void changeMatchType(){
            this.fullNameMatch = !this.fullNameMatch;
        }

        public String filterNameToString(){
            return String.format("%s match by '%s'", (fullNameMatch ? "Full ": "Part"), namePattern);
        }

        public List<Tasks> filterTasksByName(List<Tasks> tasksList){
            if (isFullNameMatch()){
                tasksList = taskController.getTaskWithNameLike(namePattern, tasksList);

            }else {
                tasksList = taskController.getTaskWithName(namePattern, tasksList);

            }
            return tasksList;
        }



        // 0 - before
        // 1 - after
        // 2 - equals
        private int alertTimeType = 0;
        private Date alertTimePattern = new Date();
        private boolean isAlertTimeFilter = false;
        public void setAlertTimeFilter(Date alertTimeFilter) {
            if(!isAlertTimeFilter)
                isAlertTimeFilter = true;
            this.alertTimePattern = alertTimeFilter;
        }

        public void clearAlertTimeFilter(){
            isAlertTimeFilter = false;
            this.alertTimePattern = new Date();
            this.alertTimeType = 0;
        }

        public void changeAlertTimeType(){
            alertTimeType = (alertTimeType + 1) % 3;
        }


        public List<Tasks> filterTasksByAlertTime(List<Tasks> tasksList){
            switch (alertTimeType){
                case 0:
                    tasksList = taskController.getTasksWithAlertTimeBefore(alertTimePattern, tasksList);
                    break;
                case 1:
                    tasksList = taskController.getTasksWithAlertTimeAfter(alertTimePattern, tasksList);
                    break;
                case 2:
                    tasksList = taskController.getTasksWithAlertTimeEquals(alertTimePattern, tasksList);
                    break;
            }

            return tasksList;
        }


        public String filterAlertTimeToString(){
            return String.format("Date %s '%s'", (alertTimeType == 0 ? "before ": (alertTimeType == 1? "after": "equals")), alertTimePattern.toString());
        }




        private boolean onlyAvailableTask = false;
        private String userNamePattern = "";
        private String firstNamePattern = "";
        private String lastNamePattern = "";
        private boolean isUserFilter = false;

        public void setUserNamePattern(String usernamePattern) {
            if(!isUserFilter)
                isUserFilter = true;
            this.userNamePattern = usernamePattern;
        }
        public void changeAvailableTask(){
            if(!isUserFilter)
                isUserFilter = true;
            this.onlyAvailableTask = !this.onlyAvailableTask;
        }

        public void setFirstNamePattern(String firstNamePattern) {
            if(!isUserFilter)
                isUserFilter = true;
            this.firstNamePattern = firstNamePattern;
        }

        public void setLastNamePattern(String lastNamePattern) {
            if(!isUserFilter)
                isUserFilter = true;
            this.lastNamePattern = lastNamePattern;
        }

        public void clearUserFilter(){
            isNameFilter = false;
            onlyAvailableTask = false;
            this.userNamePattern = "";
            this.firstNamePattern = "";
            this.lastNamePattern = "";
        }

        public List<Tasks> filterTasksByUser(List<Tasks> tasksList){
            List<Tasks> resultList = new LinkedList<>(tasksList);
            if(onlyAvailableTask){
                resultList = (taskController.getAvailableTasks(user, tasksList));

            }

            resultList = taskController.getTasksWithCreatorLike(userNamePattern, firstNamePattern, lastNamePattern, resultList);



            return resultList;
        }


        public String filterUserToString(){
            String res = "";
            if (onlyAvailableTask)
                res = "Only Available to User Tasks";
            else
                res = "All task";
            res += String.format(" with User username: '%s'\tfirstName: '%s'\tlastName: '%s'", userNamePattern, firstNamePattern, lastNamePattern);
            return res;
        }




        private boolean onlyAvailableList = false;
        private String listNamePattern = "";
        private boolean isListFilter = false;


        public void changeAvailableList(){
            if(!isListFilter)
                isListFilter = true;
            onlyAvailableList = !onlyAvailableList;
        }

        public void setListNamePattern(String listNamePattern) {
            if(!isListFilter)
                isListFilter = true;
            this.listNamePattern = listNamePattern;
        }

        public void clearListFilter(){
            onlyAvailableList = false;
            listNamePattern = "";
            isListFilter = false;
        }

        public List<Tasks> filterTasksByList(List<Tasks> tasksList){
            List<Tasks> resultList = new LinkedList<>(tasksList);
            if(onlyAvailableList) {
                resultList = listOfTasksController.getTaskWithListAvailableToUser(user, resultList);
            }
            resultList = listOfTasksController.getTasksWithListNameLike(listNamePattern, resultList);


            return resultList;
        }

        public String filterListToString(){
            String res = "";
            if (onlyAvailableList)
                res = "Only Available to User Tasks in Lists";
            else
                res = "All task";
            res += String.format(" with List name: '%s'", listNamePattern);
            return res;
        }

        public void clearAllFilter(){
            clearNameFilter();
            clearAlertTimeFilter();
            clearUserFilter();
            clearListFilter();
        }


        public List<Tasks> getTaskByFilters(){
            List<Tasks> tasksList = taskController.getAll();
            if(isNameFilter)
                tasksList = this.filterTasksByName(tasksList);
            if (isAlertTimeFilter)
                tasksList = this.filterTasksByAlertTime(tasksList);
            if (isUserFilter)
                tasksList = this.filterTasksByUser(tasksList);
            if (isListFilter)
                tasksList = this.filterTasksByList(tasksList);
            return tasksList;
        }

        public void showFilterOutTaskList(){
            List<Tasks> tasksList = getTaskByFilters();
            System.out.println("\n\n");
            if (tasksList.size() == 0)
                System.out.println("Tasks with those filter not found");
            for (int i = 0; i < tasksList.size(); i++){
                Tasks task = tasksList.get(i);
                System.out.println(String.format("%d: Task - %s: alert time %s", i + 1, task.getName(), task.getAlert_time().toString()));
            }
        }


    }

    public FindTaskView(UserController userController, TaskController taskController, ListOfTasksController listOfTasksController) {
        this.userController = userController;
        this.taskController = taskController;
        this.listOfTasksController = listOfTasksController;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public FindTaskViewResult mainMenu() {
        System.out.println("\n\nChoose action to do:");
        System.out.println("1. Filter by name");
        System.out.println("2. Filter by alert time");
        System.out.println("3. Filter by user");
        System.out.println("4. Filter by list of task");


        System.out.println("5. Show result");
        System.out.println("6. Clear filters");
        System.out.println("7. Back to main menu");

        Scanner sc = new Scanner(System.in);
        System.out.print("Input a number: ");
        int num = sc.nextInt();

        if (num >= 1 && num <= mainMenuActionCount) {
            switch (num) {
                case 1:
                    // Filter by name
                    return FindTaskViewResult.FILTER_BY_NAME;
                case 2:
                    // Filter by alert time
                    return FindTaskViewResult.FILTER_BY_ALERT_TIME;
                case 3:
                    // Filter by user
                    return FindTaskViewResult.FILTER_BY_USER;
                case 4:
                    // Filter by list of task
                    return FindTaskViewResult.FILTER_BY_LIST;
                case 5:
                    // Show result
                    filterTask.showFilterOutTaskList();
                    return FindTaskViewResult.SHOW_RESULT;
                case 6:
                    // Clear filters
                    filterTask.clearAllFilter();
                    return FindTaskViewResult.CLEAR_ALL_FILTERS;
                case 7:
                    // Back to main menu
                    return FindTaskViewResult.BACK_TO_MAIN_MENU;
            }
        } else {
            System.out.println("\n\nWrong input!!!\n");
            return FindTaskViewResult.WRONG_INPUT;
        }
        return FindTaskViewResult.BACK_TO_MAIN_MENU;
    }

    public FindTaskViewResult filterByNameView(){
        System.out.println("Filter by name: " + filterTask.filterNameToString());
        System.out.println("\n\nChoose action to do:");
        System.out.println("1. Change match to part/full");
        System.out.println("2. Type the name");
        System.out.println("3. Clear filter");
        System.out.println("4. Back to filter menu");
        System.out.println("5. Back to main menu");

        Scanner sc = new Scanner(System.in);
        System.out.print("Input a number: ");
        int num = sc.nextInt();

        if (num >= 1 && num <= filterByNameActionCount) {
            switch (num) {
                case 1:
                    // Filter by name
                    filterTask.changeMatchType();
                    return FindTaskViewResult.CHANGE_MATCH_TYPE;
                case 2:
                    // Filter by alert time
                    sc = new Scanner(System.in);
                    System.out.print("Input a name pattern: ");
                    String namePattern = sc.nextLine();
                    filterTask.setNamePattern(namePattern);
                    return FindTaskViewResult.SET_NAME_PATTERN;
                case 3:
                    // CLEAR_FILTER
                    filterTask.clearNameFilter();
                    System.out.println("\nFilter by name clear\n");
                    return FindTaskViewResult.CLEAR_FILTER;
                case 4:
                    // BACK_TO_FILTER_MENU
                    return FindTaskViewResult.BACK_TO_FILTER_MENU;
                case 5:
                    // BACK_TO_MAIN_MENU
                    return FindTaskViewResult.BACK_TO_MAIN_MENU;
            }
        } else {
            System.out.println("\n\nWrong input!!!\n");
            return FindTaskViewResult.WRONG_INPUT;
        }
        return FindTaskViewResult.BACK_TO_MAIN_MENU;

    }

    public FindTaskViewResult filterByAlertTimeView(){
        System.out.println("Filter by date: " + filterTask.filterAlertTimeToString());
        System.out.println("\n\nChoose action to do:");
        System.out.println("1. Change match to before/after/equals");
        System.out.println("2. Type the date");
        System.out.println("3. Clear filter");
        System.out.println("4. Back to filter menu");
        System.out.println("5. Back to main menu");

        Scanner sc = new Scanner(System.in);
        System.out.print("Input a number: ");
        int num = sc.nextInt();

        if (num >= 1 && num <= filterByAlertTimeActionCount) {
            switch (num) {
                case 1:
                    // Filter by name
                    filterTask.changeAlertTimeType();
                    return FindTaskViewResult.CHANGE_ALERT_TIME_TYPE;
                case 2:
                    // Filter by alert time
                    sc = new Scanner(System.in);
                    System.out.print("Input a name pattern: ");
                    String date = sc.nextLine();
                    try {
                        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        Date dateNew = dateFormat.parse(date);
                        filterTask.setAlertTimeFilter(dateNew);
                    } catch (ParseException e) {
                        System.out.println("\n\nWrong date format\n");
                        return FindTaskViewResult.WRONG_INPUT;
                    }
                    return FindTaskViewResult.SET_ALERT_TIME;
                case 3:
                    // CLEAR_FILTER
                    filterTask.clearAlertTimeFilter();
                    return FindTaskViewResult.CLEAR_FILTER;
                case 4:
                    // BACK_TO_FILTER_MENU
                    return FindTaskViewResult.BACK_TO_FILTER_MENU;
                case 5:
                    // BACK_TO_MAIN_MENU
                    return FindTaskViewResult.BACK_TO_MAIN_MENU;
            }
        } else {
            System.out.println("\n\nWrong input!!!\n");
            return FindTaskViewResult.WRONG_INPUT;
        }
        return FindTaskViewResult.BACK_TO_MAIN_MENU;

    }

    public FindTaskViewResult filterByUserView(){
        System.out.println("Filter by user: " + filterTask.filterUserToString());
        System.out.println("\n\nChoose action to do:");
        System.out.println("1. Set only available tasks to user");
        System.out.println("2. Type the userName");
        System.out.println("3. Type the firstName");
        System.out.println("4. Type the lastName");
        System.out.println("5. Clear filter");
        System.out.println("6. Back to filter menu");
        System.out.println("7. Back to main menu");

        Scanner sc = new Scanner(System.in);
        System.out.print("Input a number: ");
        int num = sc.nextInt();


        if (num >= 1 && num <= filterByUserActionCount) {
            switch (num) {
                case 1:
                    filterTask.changeAvailableTask();
                    return FindTaskViewResult.CHANGE_AVAILABLE_TASKS;
                case 2:
                    // Filter by userName
                    sc = new Scanner(System.in);
                    System.out.print("Input a username pattern: ");
                    String usernamePattern = sc.nextLine();
                    filterTask.setUserNamePattern(usernamePattern);
                    return FindTaskViewResult.SET_USERNAME_PATTERN;
                case 3:
                    // Filter by firstName
                    sc = new Scanner(System.in);
                    System.out.print("Input a first name pattern: ");
                    String firstNamePattern = sc.nextLine();
                    filterTask.setNamePattern(firstNamePattern);
                    return FindTaskViewResult.SET_FIRST_NAME_PATTERN;
                case 4:
                    // Filter by lastName
                    sc = new Scanner(System.in);
                    System.out.print("Input a last name pattern: ");
                    String lastNamePattern = sc.nextLine();
                    filterTask.setNamePattern(lastNamePattern);
                    return FindTaskViewResult.SET_LAST_NAME_PATTERN;
                case 5:
                    // CLEAR_FILTER
                    filterTask.clearUserFilter();
                    return FindTaskViewResult.CLEAR_FILTER;
                case 6:
                    // BACK_TO_FILTER_MENU
                    return FindTaskViewResult.BACK_TO_FILTER_MENU;
                case 7:
                    // BACK_TO_MAIN_MENU
                    return FindTaskViewResult.BACK_TO_MAIN_MENU;
            }
        } else {
            System.out.println("\n\nWrong input!!!\n");
            return FindTaskViewResult.WRONG_INPUT;
        }
        return FindTaskViewResult.BACK_TO_MAIN_MENU;

    }

    public FindTaskViewResult filterByListView(){
        System.out.println("Filter by list: " + filterTask.filterListToString());
        System.out.println("\n\nChoose action to do:");
        System.out.println("1. Only list available");
        System.out.println("2. Type the list name");
        System.out.println("3. Clear filter");
        System.out.println("4. Back to filter menu");
        System.out.println("5. Back to main menu");

        Scanner sc = new Scanner(System.in);
        System.out.print("Input a number: ");
        int num = sc.nextInt();

        if (num >= 1 && num <= filterByListActionCount) {
            switch (num) {
                case 1:
                    filterTask.changeAvailableList();
                    return FindTaskViewResult.CHANGE_AVAILABLE_LISTS;
                case 2:
                    // Filter by list name
                    sc = new Scanner(System.in);
                    System.out.print("Input a list name pattern: ");
                    String listName = sc.nextLine();
                    filterTask.setListNamePattern(listName);
                    return FindTaskViewResult.SET_LIST_NAME_PATTERN;
                case 3:
                    // CLEAR_FILTER
                    filterTask.clearListFilter();
                    return FindTaskViewResult.CLEAR_FILTER;
                case 4:
                    // BACK_TO_FILTER_MENU
                    return FindTaskViewResult.BACK_TO_FILTER_MENU;
                case 5:
                    // BACK_TO_MAIN_MENU
                    return FindTaskViewResult.BACK_TO_MAIN_MENU;
            }
        } else {
            System.out.println("\n\nWrong input!!!\n");
            return FindTaskViewResult.WRONG_INPUT;
        }
        return FindTaskViewResult.BACK_TO_MAIN_MENU;

    }

}
