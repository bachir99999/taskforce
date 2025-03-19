package fr.op.taskforce.task;

import fr.op.taskforce.task.dto.TaskDTO;
import fr.op.taskforce.task.dto.TaskResponseDTO;
import fr.op.taskforce.task.entity.Task;
import fr.op.taskforce.task.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public List<TaskResponseDTO> getAllTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/{id}")
    public TaskResponseDTO getTaskById(@PathVariable Integer id) {
        return taskService.getTaskById(id);
    }

    @PostMapping
    public TaskResponseDTO createTask(@RequestBody TaskDTO taskDTO) {
        return taskService.saveTask(taskDTO);
    }

    @PutMapping("/{id}")
    public TaskResponseDTO updateTask(@PathVariable Integer id, @RequestBody TaskDTO taskDTO) {
        return taskService.updateTask(id, taskDTO);
    }

    @PatchMapping("/{id}")
    public TaskResponseDTO patchTask(@PathVariable Integer id, @RequestBody TaskDTO taskDTO) {
        return taskService.patchTask(id, taskDTO);
    }


    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Integer id) {
        taskService.deleteTask(id);
    }
}
