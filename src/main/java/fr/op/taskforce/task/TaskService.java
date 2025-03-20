package fr.op.taskforce.task;

import fr.op.taskforce.task.dto.TaskDTO;
import fr.op.taskforce.task.dto.TaskResponseDTO;
import fr.op.taskforce.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TaskMapper taskMapper;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.taskMapper = taskMapper;
    }


    public List<TaskResponseDTO> getAllTasks() {
        return taskMapper.taskListToTaskResponseDTOList(taskRepository.findAll());
    }

    public TaskResponseDTO getTaskById(Integer id) {
        return taskMapper.taskToTaskResponseDTO(taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found with id " + id)));
    }

    public TaskResponseDTO saveTask(TaskDTO taskDTO) {
        var assignedUser = userRepository.findById(taskDTO.assignedToId()).orElseThrow(() -> new RuntimeException("User not found with id " + taskDTO.assignedToId()));
        return taskMapper.taskToTaskResponseDTO(taskRepository.save(taskMapper.taskDTOToTask(taskDTO, assignedUser)));
    }

    public TaskResponseDTO updateTask(Integer id, TaskDTO taskDTO) {
        var existingTask = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found with id " + id));
        existingTask.setName(taskDTO.name());
        existingTask.setDescription(taskDTO.description());
        existingTask.setDueDate(taskDTO.dueDate());
        existingTask.setStatus(taskDTO.status());
        existingTask.setAssignedTo(userRepository.findById(taskDTO.assignedToId()).orElseThrow(() -> new RuntimeException("User not found with id " + taskDTO.assignedToId())));
        return taskMapper.taskToTaskResponseDTO(taskRepository.save(existingTask));
    }

    public TaskResponseDTO patchTask(Integer id, TaskDTO taskDTO) {
        var existingTask = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found with id " + id));
        if (existingTask == null) return null;
        if (taskDTO.name() != null) existingTask.setName(taskDTO.name());
        if (taskDTO.description() != null) existingTask.setDescription(taskDTO.description());
        if (taskDTO.dueDate() != null) existingTask.setDueDate(taskDTO.dueDate());
        if (taskDTO.status() != null) existingTask.setStatus(taskDTO.status());
        if (taskDTO.assignedToId() != null)
            existingTask.setAssignedTo(userRepository.findById(taskDTO.assignedToId()).orElseThrow(() -> new RuntimeException("User not found with id " + taskDTO.assignedToId())));

        return taskMapper.taskToTaskResponseDTO(taskRepository.save(existingTask));
    }

    public void deleteTask(Integer id) {
        if (!taskRepository.existsById(id)) {
            throw new RuntimeException("Task not found with id " + id);
        }

        taskRepository.deleteById(id);
    }
}
