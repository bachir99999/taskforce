package fr.op.taskforce.user;

import fr.op.taskforce.user.dto.UserDTO;
import fr.op.taskforce.user.dto.UserResponseDTO;
import fr.op.taskforce.user.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class UserMapperTest {

    private UserMapper userMapper;

    @BeforeEach
    void setUp() {
        userMapper = new UserMapper();
    }

    @Test
    void userToResponseDTO_shouldConvertUserToDTO() {
        // Arrange
        var user = new User("John Doe", "johndoe@example.com", "password");
        user.setId(1);
        // Act
        var result = userMapper.userToResponseDTO(user);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(1);
        assertThat(result.name()).isEqualTo("John Doe");
        assertThat(result.email()).isEqualTo("johndoe@example.com");
    }

    @Test
    void userToResponseDTO_shouldReturnNullWhenUserIsNull() {
        // Act
        var result = userMapper.userToResponseDTO(null);

        // Assert
        assertThat(result).isNull();
    }

    @Test
    void userDTOToUser_shouldConvertDTOToUser() {
        // Arrange
        var userDTO = new UserDTO("Jane Doe", "janedoe@example.com", "secure123");

        // Act
        var result = userMapper.userDTOToUser(userDTO);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Jane Doe");
        assertThat(result.getEmail()).isEqualTo("janedoe@example.com");
        assertThat(result.getPassword()).isEqualTo("secure123");
    }

    @Test
    void userDTOToUser_shouldReturnNullWhenDTOIsNull() {
        // Act
        var result = userMapper.userDTOToUser(null);

        // Assert
        assertThat(result).isNull();
    }

    @Test
    void usersToResponseDTOs_shouldConvertListOfUsersToDTOs() {
        // Arrange
        var users = List.of(
                new User("Alice", "alice@example.com", "pass1"),
                new User("Bob", "bob@example.com", "pass2")
        );

        // Act
        var result = userMapper.usersToResponseDTOs(users);

        // Assert
        assertThat(result).hasSize(2);
        assertThat(result.get(0).name()).isEqualTo("Alice");
        assertThat(result.get(1).name()).isEqualTo("Bob");
    }

    @Test
    void usersToResponseDTOs_shouldReturnEmptyListWhenUsersListIsEmpty() {
        // Act
        var result = userMapper.usersToResponseDTOs(List.of());

        // Assert
        assertThat(result).isEmpty();
    }

    @Test
    void usersDTOToUsers_shouldConvertListOfUserDTOsToUsers() {
        // Arrange
        var userDTOs = List.of(
                new UserDTO("Charlie", "charlie@example.com", "secret1"),
                new UserDTO("Dana", "dana@example.com", "secret2")
        );

        // Act
        var result = userMapper.usersDTOToUsers(userDTOs);

        // Assert
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getName()).isEqualTo("Charlie");
        assertThat(result.get(1).getName()).isEqualTo("Dana");
    }

    @Test
    void usersDTOToUsers_shouldReturnEmptyListWhenDTOListIsEmpty() {
        // Act
        var result = userMapper.usersDTOToUsers(List.of());

        // Assert
        assertThat(result).isEmpty();
    }
}
