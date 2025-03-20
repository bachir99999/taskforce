package fr.op.taskforce.user.dto;

import jakarta.validation.constraints.*;

public record UserDTO(
        @NotBlank
        @Size(max = 25)
        String name,
        @Email
        @Size(max = 25)
        @NotBlank
        String email,
        @NotBlank
        @Size(max = 25)
        String password
) { }
