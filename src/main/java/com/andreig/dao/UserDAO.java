package com.andreig.dao;

import com.andreig.model.Task;
import com.andreig.model.User;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    private final static String ALREADY_EXISTS = "Such user name already exists";
    private final static String USER_ADDED = "User has been successfully added";
    private final static String USER_NOT_FOUND = "Such user name not found";
    private final static String TASK_ASSIGNED = "Task has been assigned to user %s \n";
    private final static String NO_TASKS_FOUND = "User name %s doesn't have any tasks assigned \n";

    public static void createUser(User user){
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("src/main/dataFile.csv"));
            StringBuffer stringBuffer = new StringBuffer();
            String currentLine;

            while((currentLine = bufferedReader.readLine()) != null) {
                if (currentLine.split(", ")[2].equals(user.getUserName())){
                    System.out.println(ALREADY_EXISTS);
                    bufferedReader.close();
                    return;

                }
                stringBuffer.append(currentLine + '\n');
            }
            bufferedReader.close();

            StringBuilder userData = new StringBuilder();
            userData.append(user.getFirstName() + ", ");
            userData.append(user.getLastName() + ", ");
            userData.append(user.getUserName()+ ", ");

            stringBuffer.append(userData);

            FileOutputStream fileOutputStream = new FileOutputStream("src/main/dataFile.csv");
            fileOutputStream.write(stringBuffer.toString().getBytes(StandardCharsets.UTF_8));
            fileOutputStream.close();

            System.out.println(USER_ADDED);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<User> showAllUsers(){
        List<User> result = new ArrayList<>();

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("src/main/dataFile.csv"));
            StringBuffer stringBuffer = new StringBuffer();

            String currentLine;

            while((currentLine = bufferedReader.readLine()) != null) {
                String[] userInfo= currentLine.split(", ");
                result.add(new User(userInfo[0], userInfo[1], userInfo[2]));
            }
            bufferedReader.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static void assignTask(String userName, Task task) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("src/main/dataFile.csv"));
            StringBuffer stringBuffer = new StringBuffer();

            String currentUserLine = null;
            String line;

            while((line = bufferedReader.readLine()) != null){
                if (line.split(", ")[2].equals(userName)){
                    currentUserLine = line;
                }
                else {
                    stringBuffer.append(line + '\n');
                }
            }

            if (currentUserLine == null){
                System.out.println(USER_NOT_FOUND);
                bufferedReader.close();
                return;
            }

            String taskInfo = "Title: " + task.getTitle() + " /Description: " + task.getDescription() + "// ";
            bufferedReader.close();
            if (currentUserLine.split("Tasks: ").length > 1) {
                stringBuffer.append(currentUserLine + taskInfo);
            }
            else {
                stringBuffer.append(currentUserLine + "Tasks: " + taskInfo);
            }

            FileOutputStream fileOutputStream = new FileOutputStream("src/main/dataFile.csv");
            fileOutputStream.write(stringBuffer.toString().getBytes(StandardCharsets.UTF_8));
            fileOutputStream.close();

            System.out.format(TASK_ASSIGNED, userName);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Task> getUserTasks(String userName){
        ArrayList<Task> tasks = new ArrayList<>();

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("src/main/dataFile.csv"));
            StringBuffer stringBuffer = new StringBuffer();

            String currentLine;
            while((currentLine = bufferedReader.readLine()) != null){
                if (currentLine.split(", ")[2].equals(userName)){
                    if (currentLine.split("Tasks: ").length < 1){
                        System.out.format(NO_TASKS_FOUND, userName);
                        bufferedReader.close();
                        return null;
                    }
                    else {
                        String[] taskArr = currentLine.split("Tasks: ")[1].split("// ");
                        for (int i = 0; i< taskArr.length; i++){
                            tasks.add(new Task(taskArr[i].split(" /")[0], taskArr[i].split(" /")[1]));
                        }
                    }
                }
            }
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tasks;
    }

    public static void main(String[] args) {
        User user = new User("f","fd", "fd");
        createUser(user);

        for (int i = 1; i <6; i++){
            Task task = new Task("Task"+i, "Description" + i);
            assignTask("fd", task);
        }
        System.out.println(getUserTasks("fd").toString());
    }
}
