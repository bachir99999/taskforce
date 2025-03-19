package fr.op.taskforce.task;

import fr.op.taskforce.task.dto.TaskDTO;
import fr.op.taskforce.task.dto.TaskResponseDTO;
import fr.op.taskforce.task.entity.Task;
import fr.op.taskforce.user.UserMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final UserMapper userMapper;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
        this.taskMapper = new TaskMapper();
        this.userMapper = new UserMapper();
    }


    public List<TaskResponseDTO> getAllTasks() {
        return taskMapper.taskListToTaskResponseDTOList(taskRepository.findAll());
    }

    public TaskResponseDTO getTaskById(Integer id) {
        return taskMapper.taskToTaskResponseDTO(taskRepository.findById(id).orElse(null));
    }

    public TaskResponseDTO saveTask(TaskDTO taskDTO) {
        return taskMapper.taskToTaskResponseDTO(taskRepository.save(taskMapper.taskDTOToTask(taskDTO)));
    }

    public TaskResponseDTO updateTask(Integer id, TaskDTO taskDTO) {
        var existingTask= taskRepository.findById(id).orElse(new Task());
            existingTask.setName(taskDTO.name());
            existingTask.setDescription(taskDTO.description());
            existingTask.setDueDate(taskDTO.dueDate());
            existingTask.setStatus(taskDTO.status());
            existingTask.setAssignedTo(userMapper.userDTOToUser(taskDTO.userDTO()));
            return  taskMapper.taskToTaskResponseDTO(taskRepository.save(existingTask));
    }

    public TaskResponseDTO patchTask(Integer id,  TaskDTO taskDTO) {
        var existingTask= taskRepository.findById(id).orElse(null);
        if (existingTask == null) return null;
        if (taskDTO.name() != null) existingTask.setName(taskDTO.name());
        if (taskDTO.description() != null) existingTask.setDescription(taskDTO.description());
        if (taskDTO.dueDate() != null) existingTask.setDueDate(taskDTO.dueDate());
        if (taskDTO.status() != null) existingTask.setStatus(taskDTO.status());
        if (taskDTO.userDTO() != null) existingTask.setAssignedTo(userMapper.userDTOToUser(taskDTO.userDTO()));

        return  taskMapper.taskToTaskResponseDTO(taskRepository.save(existingTask));
    }

    public void deleteTask(Integer id) {
        // if (taskRepository.existsById(id)) {
        taskRepository.deleteById(id);
    }
}
