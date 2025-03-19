package fr.op.taskforce.task.dto;

import fr.op.taskforce.task.entity.Task;
import fr.op.taskforce.user.dto.UserDTO;

import java.time.LocalDate;

public record TaskDTO(String name, String description, LocalDate dueDate, Task.Status status, UserDTO userDTO) {
}
