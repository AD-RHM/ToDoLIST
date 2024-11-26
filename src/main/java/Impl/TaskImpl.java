package Impl;

import DB_Connection.ConnectionDB;
import DTO.Priority;
import DTO.Status;
import Interfaces.TaskInterface;
import DTO.Task;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TaskImpl implements TaskInterface {

    @Override
    public Boolean addTask(Task task) throws SQLException, ClassNotFoundException {
        String query = "insert into tasks (name, description, priority, status, createdAt, updatedAt) values (?,?,?,?,?,?)";
        try(var connectionDB = ConnectionDB.getConnection();
            PreparedStatement ps = connectionDB.prepareStatement(query)) {
            ps.setString(1,task.getName());
            ps.setString(2,task.getDescription());
            ps.setString(3,String.valueOf(task.getPriority()));
            ps.setString(4,String.valueOf(task.getStatus()));
            ps.setDate(5,Date.valueOf(task.getCreatedAt()));
            ps.setDate(6, Date.valueOf(task.getUpdatedAt()));
            if (ps.executeUpdate() > 0) {
                return true;
            }else return false;
        }
    }

    @Override
    public Boolean deleteTask(int id) throws SQLException, ClassNotFoundException {
        String query = "delete from tasks where id = ?";
        try(var connectionDB = ConnectionDB.getConnection();
        PreparedStatement ps = connectionDB.prepareStatement(query)) {
            ps.setInt(1,id);
            if (ps.executeUpdate() > 0) {
                return true;
            }else return false;
        }
    }

    @Override
    public Boolean updateTask(Task task) throws SQLException, ClassNotFoundException {
        String query = "Update tasks set name = ?, description = ?, priority = ?, status = ?, updatedAt = ? where id = ?";
        try(var connectionDB = ConnectionDB.getConnection();
        PreparedStatement preparedStatement = connectionDB.prepareStatement(query)){
            preparedStatement.setString(1,task.getName());
            preparedStatement.setString(2,task.getDescription());
            preparedStatement.setString(3,String.valueOf(task.getPriority()));
            preparedStatement.setString(4,String.valueOf(task.getStatus()));
            preparedStatement.setDate(5,Date.valueOf(LocalDate.now()));
            preparedStatement.setLong(6,task.getId());
            if (preparedStatement.executeUpdate() > 0) {
                return true;
            }else return false;

        }
    }

    @Override
    public List<Task> getTasks() throws SQLException, ClassNotFoundException {
        Task task = null;
        List<Task> tasks = new ArrayList<>();
        String query = "select * from tasks";
        try(var connectionDB = ConnectionDB.getConnection();
            var ps = connectionDB.prepareStatement(query)){
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    task = new Task();
                    task.setId(rs.getLong("id"));
                    task.setName(rs.getString("name"));
                    task.setDescription(rs.getString("description"));
                    task.setPriority(Priority.valueOf(rs.getString("priority")));
                    task.setStatus(Status.valueOf(rs.getString("status")));
                    task.setCreatedAt(LocalDate.parse(rs.getString("createdAt")));
                    task.setUpdatedAt(LocalDate.parse(rs.getString("updatedAt")));
                    tasks.add(task);
                }
            }
        }
        return tasks;
    }
    }


    @Override
    public List<Task> getTasksByName(String taskName) throws SQLException, ClassNotFoundException {
        Task task = null;
        List<Task> tasks = new ArrayList<>();
        String query = "select * from tasks where name = ?";
        try(var connectionDB = ConnectionDB.getConnection();
            var ps = connectionDB.prepareStatement(query)){
            ps.setString(1,taskName);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    task = new Task();
                    task.setId(rs.getLong("id"));
                    task.setName(rs.getString("name"));
                    task.setDescription(rs.getString("description"));
                    task.setPriority(Priority.valueOf(rs.getString("priority")));
                    task.setStatus(Status.valueOf(rs.getString("status")));
                    task.setCreatedAt(LocalDate.parse(rs.getString("createdAt")));
                    task.setUpdatedAt(LocalDate.parse(rs.getString("updatedAt")));
                    tasks.add(task);
                }
            }
        }
        return tasks;
    }


}



