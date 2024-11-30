package Repositories;

import Model.Priority;
import Model.Status;
import Model.Task;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TaskImpl implements TaskInterface {

    @Override
    public Boolean addTask(Task task) {
        String query = "insert into tasks (name, status, priority) values (?,?,?)";
        try (var connectionDB = ConnectionDB.getConnection();
             PreparedStatement ps = connectionDB.prepareStatement(query)) {
            ps.setString(1, task.getName());
            ps.setString(2, String.valueOf(task.getStatus()));
            ps.setString(3, String.valueOf(task.getPriority()));
            return ps.executeUpdate() > 0;
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException("Error adding task", e);
        }
    }

    @Override
    public Boolean deleteTask(int id) {
        String query = "delete from tasks where id = ?";
        try (var connectionDB = ConnectionDB.getConnection();
             PreparedStatement ps = connectionDB.prepareStatement(query)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException("Error deleting task", e);
        }
    }

    @Override
    public Boolean updateTask(Task task) {
        String query = "Update tasks set name = ?, priority = ?, status = ? where id = ?";
        try (var connectionDB = ConnectionDB.getConnection();
             PreparedStatement ps = connectionDB.prepareStatement(query)) {
            ps.setString(1, task.getName());
            ps.setString(2, String.valueOf(task.getPriority()));
            ps.setString(3, String.valueOf(task.getStatus()));
            ps.setLong(4, task.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException("Error updating task", e);
        }
    }

    @Override
    public List<Task> getTasks() {
        List<Task> tasks = new ArrayList<>();
        String query = "select * from tasks";
        try (var connectionDB = ConnectionDB.getConnection();
             var ps = connectionDB.createStatement()) {
                 try (ResultSet rs = ps.executeQuery(query)) {
                     while (rs.next()) {
                         Task task = new Task();
                         task.setId(rs.getLong("id"));
                         task.setName(rs.getString("name"));
                         task.setDescription(rs.getString("description"));
                         task.setPriority(Priority.valueOf(rs.getString("priority")));
                         task.setStatus(Status.valueOf(rs.getString("status")));
                         task.setCreatedAt(rs.getTimestamp("created_At").toLocalDateTime());
                         task.setUpdatedAt(rs.getTimestamp("updated_At").toLocalDateTime());
                         tasks.add(task);
                     }
                 }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException("Error fetching tasks", e);
        }
        tasks.sort(Comparator.comparing(Task::getStatus).thenComparing(Task::getPriority));
        return tasks;
    }

    @Override
    public List<Task> getTasksByStatus(Status status) {
        List<Task> tasks = new ArrayList<>();
        String query = "select * from tasks where status = ?";
        try (var connectionDB = ConnectionDB.getConnection();
             var ps = connectionDB.prepareStatement(query)) {
            ps.setString(1, String.valueOf(status));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Task task = new Task();
                    task.setId(rs.getLong("id"));
                    task.setName(rs.getString("name"));
                    task.setDescription(rs.getString("description"));
                    task.setPriority(Priority.valueOf(rs.getString("priority")));
                    task.setStatus(Status.valueOf(rs.getString("status")));
                    task.setCreatedAt(rs.getTimestamp("created_At").toLocalDateTime());
                    task.setUpdatedAt(rs.getTimestamp("updated_At").toLocalDateTime());
                    tasks.add(task);
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException("Error fetching tasks by status", e);
        }
        tasks.sort(Comparator.comparing(Task::getStatus).thenComparing(Task::getPriority));
        return tasks;
    }

}
