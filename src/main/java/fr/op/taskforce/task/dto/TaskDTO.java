package fr.op.taskforce.task.dto;

import fr.op.taskforce.task.entity.Task;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record TaskDTO(
        @NotBlank
        @Size(max = 25)
        String name,

        @Size(max = 255)
        String description,
        @NotNull
        LocalDate dueDate,
        @NotNull
        Task.Status status,
        @NotNull
        @Positive
        Integer assignedToId
) { }

