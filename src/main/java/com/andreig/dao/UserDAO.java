package com.andreig.dao;

import com.andreig.model.Task;
import com.andreig.model.User;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class UserDAO implements UserDAOService {

    @Override
    public void createUser(User user){
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("dataFile.csv"));
            StringBuilder stringBuilder = new StringBuilder();
            String currentLine;

            while((currentLine = bufferedReader.readLine()) != null) {
                if (currentLine.split(", ")[2].equals(user.getUserName())){
                    System.out.println(ALREADY_EXISTS);
                    bufferedReader.close();
                    return;

                }
                stringBuilder.append(currentLine);
                stringBuilder.append('\n');
            }
            bufferedReader.close();

            StringBuilder userData = new StringBuilder();
            userData.append(user.getFirstName());
            userData.append(", ");
            userData.append(user.getLastName());
            userData.append(", ");
            userData.append(user.getUserName());
            userData.append(", ");

            stringBuilder.append(userData);

            FileOutputStream fileOutputStream = new FileOutputStream("dataFile.csv");
            fileOutputStream.write(stringBuilder.toString().getBytes(StandardCharsets.UTF_8));
            fileOutputStream.close();

            System.out.println(USER_ADDED);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> showAllUsers(){
        List<User> result = new ArrayList<>();

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("dataFile.csv"));
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

    @Override
    public void assignTask(String userName, Task task) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("dataFile.csv"));
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

            FileOutputStream fileOutputStream = new FileOutputStream("dataFile.csv");
            fileOutputStream.write(stringBuffer.toString().getBytes(StandardCharsets.UTF_8));
            fileOutputStream.close();

            System.out.format(TASK_ASSIGNED, userName);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Task> getUserTasks(String userName){
        ArrayList<Task> tasks = new ArrayList<>();

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("dataFile.csv"));
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

}
