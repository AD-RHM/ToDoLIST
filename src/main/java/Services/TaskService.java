package Services;

import Model.Status;
import Model.Task;
import Repositories.TaskImpl;
import Repositories.TaskInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    private final TaskInterface taskImpl;

    @Autowired
    public TaskService() {
        this.taskImpl = new TaskImpl();
    }
    public boolean addTask(Task task) {
        if (task == null || task.getName() == null || task.getName().isEmpty()) {
            throw new IllegalArgumentException("Task or Task Name cannot be null or empty.");
        }
        return taskImpl.addTask(task);
    }

    public boolean updateTask(Task task) {
        if (task == null || task.getId() == null) {
            throw new IllegalArgumentException("Task or Task ID cannot be null.");
        }
        return taskImpl.updateTask(task);
    }

    public boolean deleteTask(long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Task ID must be greater than 0.");
        }
        return taskImpl.deleteTask((int) id);
    }

    public List<Task> getAllTasks() {
        return taskImpl.getTasks();
    }

    public List<Task> getTasksByStatus(Status status) {
        if (status == null) {
            throw new IllegalArgumentException("Task Name cannot be null or empty.");
        }
        return taskImpl.getTasksByStatus(status);
    }
}
