package com.andreig.dao;

import com.andreig.model.Task;
import com.andreig.model.User;

import java.util.List;

public interface UserDAOService {
    String ALREADY_EXISTS = "Such user name already exists";
    String USER_ADDED = "User has been successfully added";
    String USER_NOT_FOUND = "Such user name not found";
    String TASK_ASSIGNED = "Task has been assigned to user %s \n";
    String NO_TASKS_FOUND = "User name %s doesn't have any tasks assigned \n";

    void createUser(User user);

    List<User> showAllUsers();

    void assignTask(String userName, Task task);

    List<Task> getUserTasks(String userName);
}
