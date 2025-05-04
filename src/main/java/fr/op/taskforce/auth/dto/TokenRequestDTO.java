package fr.op.taskforce.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record TokenRequestDTO(@NotBlank String token) { }

