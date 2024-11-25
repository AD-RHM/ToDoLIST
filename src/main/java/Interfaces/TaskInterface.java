package Interfaces;

public interface TaskInterface {
    Boolean addTask(TaskInterface task);
    Boolean deleteTask(TaskInterface task);
    Boolean updateTask(TaskInterface task);
    TaskInterface getTask(String taskName);
}
