package Interfaces;

import DTO.Task;

import java.sql.SQLException;
import java.util.List;

public interface TaskInterface {
    Boolean addTask(Task task) throws SQLException, ClassNotFoundException;
    Boolean deleteTask(int id) throws SQLException, ClassNotFoundException;
    Boolean updateTask(Task task) throws SQLException, ClassNotFoundException;
    List<Task> getTasks() throws SQLException, ClassNotFoundException;
    List<Task> getTasksByName(String name) throws SQLException, ClassNotFoundException;

}
