package fr.op.taskforce.task.dto;

import fr.op.taskforce.task.entity.Task;
import fr.op.taskforce.user.dto.UserResponseDTO;

import java.time.LocalDate;

public record TaskResponseDTO(Integer id, String name, String description, LocalDate dueDate, Task.Status status, UserResponseDTO assignedTo) {
}
