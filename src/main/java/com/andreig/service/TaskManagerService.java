package com.andreig.service;

import com.andreig.dao.UserDAO;
import com.andreig.model.Task;
import com.andreig.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TaskManagerService {

    static UserDAO userDAO = new UserDAO();

    public static void main(String[] args) {

        switch (args[0]) {
            case "-createUser" -> {
                String userName = args[3].split("=")[1];
                if (userName.startsWith("'")) {
                    userName = userName.substring(1, userName.length());
                }
                if (userName.endsWith("'")) {
                    userName = userName.substring(0, userName.length() - 1);
                }
                User user = new User(args[1].split("=")[1],
                        (args[2].split("=")[1]),
                        (userName));
                userDAO.createUser(user);
            }

            case "-showAllUsers" -> userDAO.showAllUsers().forEach(user -> System.out.println(user.toString()));


            case "-addTask" -> {
                String userName = args[1].split("=")[1];
                if (userName.startsWith("'")) {
                    userName = userName.substring(1, userName.length());
                }
                if (userName.endsWith("'")) {
                    userName = userName.substring(0, userName.length() - 1);
                }
                String taskTitle = "";
                String taskDescription = "";
                int taskDescriptionIndex = 0;
                for (int i = 2; i < args.length; i++) {
                    if (args[i].startsWith("-td")) {
                        taskDescriptionIndex = i;
                        break;
                    }
                    taskTitle = taskTitle + args[i];
                }

                for (int i = taskDescriptionIndex; i < args.length; i++) {
                    if (i == 0) {
                        System.out.println("No description provided");
                        return;
                    }
                    taskDescription = taskDescription + args[i];
                }

                Task task = new Task((taskTitle.split("=")[1]), taskDescription.split("=")[1]);
                userDAO.assignTask(userName, task);
            }
            case "-showTasks" -> {
                String userName = args[1].split("=")[1];
                if (userName.startsWith("'")) {
                    userName = userName.substring(1);
                }
                if (userName.endsWith("'")) {
                    userName = userName.substring(0, userName.length() - 1);
                }
                userDAO.getUserTasks(userName).forEach
                        (task -> System.out.println(task.toString()));
            }
            default -> System.out.println("Unknown command.");

        }
    }
}

