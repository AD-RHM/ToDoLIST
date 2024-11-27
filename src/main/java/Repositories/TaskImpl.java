package Repositories;

import Model.Priority;
import Model.Status;
import Model.Task;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TaskImpl implements TaskInterface {

    @Override
    public Boolean addTask(Task task) {
        String query = "insert into tasks (name, description, priority, status, createdAt, updatedAt) values (?,?,?,?,?,?)";
        try (var connectionDB = ConnectionDB.getConnection();
             PreparedStatement ps = connectionDB.prepareStatement(query)) {
            ps.setString(1, task.getName());
            ps.setString(2, task.getDescription());
            ps.setString(3, String.valueOf(task.getPriority()));
            ps.setString(4, String.valueOf(task.getStatus()));
            ps.setDate(5, Date.valueOf(task.getCreatedAt()));
            ps.setDate(6, Date.valueOf(task.getUpdatedAt()));
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
        String query = "Update tasks set name = ?, description = ?, priority = ?, status = ?, updatedAt = ? where id = ?";
        try (var connectionDB = ConnectionDB.getConnection();
             PreparedStatement ps = connectionDB.prepareStatement(query)) {
            ps.setString(1, task.getName());
            ps.setString(2, task.getDescription());
            ps.setString(3, String.valueOf(task.getPriority()));
            ps.setString(4, String.valueOf(task.getStatus()));
            ps.setDate(5, Date.valueOf(LocalDate.now()));
            ps.setLong(6, task.getId());
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
             var ps = connectionDB.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                tasks.add(mapRowToTask(rs));
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException("Error fetching tasks", e);
        }
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
                    tasks.add(mapRowToTask(rs));
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException("Error fetching tasks by name", e);
        }
        return tasks;
    }

    private Task mapRowToTask(ResultSet rs) throws SQLException {
        Task task = new Task();
        task.setId(rs.getLong("id"));
        task.setName(rs.getString("name"));
        task.setDescription(rs.getString("description"));
        task.setPriority(Priority.valueOf(rs.getString("priority")));
        task.setStatus(Status.valueOf(rs.getString("status")));
        task.setCreatedAt(rs.getDate("createdAt").toLocalDate());
        task.setUpdatedAt(rs.getDate("updatedAt").toLocalDate());
        return task;
    }
}
