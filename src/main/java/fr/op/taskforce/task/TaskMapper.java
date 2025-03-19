package fr.op.taskforce.task;

import fr.op.taskforce.task.dto.TaskDTO;
import fr.op.taskforce.task.dto.TaskResponseDTO;
import fr.op.taskforce.task.entity.Task;
import fr.op.taskforce.user.UserMapper;

import java.util.List;

public class TaskMapper {
    private final UserMapper userMapper;
    public TaskMapper() {
        userMapper = new UserMapper();
    }
    public TaskResponseDTO taskToTaskResponseDTO(Task task) {
        return task != null ?
                new TaskResponseDTO(task.getName(), task.getDescription(), task.getDueDate(), task.getStatus(), userMapper.userToResponseDTO(task.getAssignedTo())) : null;
    }

    public Task taskDTOToTask(TaskDTO taskDTO) {
        return taskDTO != null ? new Task(taskDTO.name(), taskDTO.description(), taskDTO.dueDate(), taskDTO.status(), userMapper.userDTOToUser(taskDTO.userDTO())) : null;
    }

    public List<TaskResponseDTO> taskListToTaskResponseDTOList(List<Task> tasks) {
        return tasks.stream()
                .map(this::taskToTaskResponseDTO)
                .toList();
    }


    public List<Task> taskDTOListToTaskList(List<TaskDTO> taskDTOs) {
        return taskDTOs.stream()
                .map(this::taskDTOToTask)
                .toList();
    }
}
