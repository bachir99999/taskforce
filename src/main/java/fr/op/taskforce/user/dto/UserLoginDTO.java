package fr.op.taskforce.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserLoginDTO(@NotBlank
                           @Size(max = 25)
                           String name, @NotBlank
                           @Size(max = 25)
                           String password) {
}
