package fr.op.taskforce.auth.dto;

import fr.op.taskforce.user.dto.UserResponseDTO;

public record AuthResponseDTO(String token,
                              String type,
                              UserResponseDTO user) {
}
