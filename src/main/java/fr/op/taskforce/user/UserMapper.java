package fr.op.taskforce.user;

import fr.op.taskforce.user.dto.UserDTO;
import fr.op.taskforce.user.dto.UserResponseDTO;
import fr.op.taskforce.user.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMapper {

    public UserResponseDTO userToResponseDTO(User user) {
        return user != null ? new UserResponseDTO(user.getId(), user.getName(), user.getEmail()) : null;
    }

    public User userDTOToUser(UserDTO userDTO) {
        return userDTO != null ? new User(userDTO.name(), userDTO.email(), userDTO.password()) : null;
    }

    public List<UserResponseDTO> usersToResponseDTOs(List<User> users) {
        return users.stream()
                .map(this::userToResponseDTO)
                .toList();
    }

    public List<User> usersDTOToUsers(List<UserDTO> usersDTO) {
        return usersDTO.stream()
                .map(this::userDTOToUser)
                .toList();
    }
}
