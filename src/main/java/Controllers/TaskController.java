package Controllers;

import Model.Status;
import Model.Priority;
import Model.Task;
import Services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class TaskController {


    private final TaskService taskService = new TaskService();

    @GetMapping("/index")
    public String showTasks(@RequestParam(value = "status",required = false) Status status, Priority priority, Model model) {
        if (status != null) {
            model.addAttribute("tasks", taskService.getTasksByStatus(status));
        } else {
            model.addAttribute("tasks", taskService.getAllTasks());
        }
        model.addAttribute("statusList", Status.values());
        model.addAttribute("priorityList", Priority.values());
        return "index";
    }

    @PostMapping("/addTask")
    public String addTask(@RequestParam String name, @RequestParam Status status, @RequestParam Priority priority) {
        Task task = new Task();
        task.setName(name);
        task.setStatus(status);
        task.setPriority(priority);
        taskService.addTask(task);
        return "redirect:/index";
    }

    @PostMapping("/deleteTask")
    public String deleteTask(@RequestParam int id) {
        taskService.deleteTask(id);
        return "redirect:/index";
    }

    @PostMapping("/updateTask")
    public String updateTask(
            @RequestParam Long id,
            @RequestParam String name,
            @RequestParam Status status,
            @RequestParam Priority priority
    ) {
        Task task = new Task();
        task.setId(id);
        task.setName(name);
        task.setStatus(status);
        task.setPriority(priority);
        taskService.updateTask(task);
        return "redirect:/index";
    }

}

