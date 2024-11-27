package Repositories;

import Model.Status;
import Model.Task;

import java.util.List;

public interface TaskInterface {
    Boolean addTask(Task task) ;
    Boolean deleteTask(int id) ;
    Boolean updateTask(Task task);
    List<Task> getTasks() ;
    List<Task> getTasksByStatus(Status status);

}
