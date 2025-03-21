package fr.op.taskforce.user;

import fr.op.taskforce.task.dto.TaskResponseDTO;
import fr.op.taskforce.task.entity.Task;
import fr.op.taskforce.user.dto.UserDTO;
import fr.op.taskforce.user.dto.UserResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private UserDTO userDTO;
    private UserResponseDTO userResponseDTO;

    @BeforeEach
    void setUp() {
        userDTO = new UserDTO("John", "johndoe@example.com", "1234");
        userResponseDTO = new UserResponseDTO(1, "John", "johndoe@example.com");
    }

    @Test
    void createUser_shouldReturnCreatedUser() {
        // Arrange
        when(userService.save(userDTO)).thenReturn(userResponseDTO);

        // Act
        var response = userController.createUser(userDTO);

        // Assert
        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getBody()).isNotNull().isEqualTo(userResponseDTO);
        verify(userService, times(1)).save(userDTO);
    }

    @Test
    void getUserById_shouldReturnUser() {
        // Arrange
        when(userService.findById(1)).thenReturn(userResponseDTO);

        // Act
        var response = userController.getUserById(1);

        // Assert
        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getBody()).isNotNull().isEqualTo(userResponseDTO);
        verify(userService, times(1)).findById(1);
    }

    @Test
    void getUserTasks_shouldReturnListOfTasks() {
        // Arrange
        UserResponseDTO assignedUser = new UserResponseDTO(1, "John", "johndoe@example.com");
        List<TaskResponseDTO> tasks = List.of(
                new TaskResponseDTO(1, "Task 1", "Description 1", LocalDate.of(2025, 3, 21), Task.Status.IN_PROGRESS, assignedUser),
                new TaskResponseDTO(2, "Task 2", "Description 2", LocalDate.of(2025, 3, 22), Task.Status.DONE, assignedUser)
        );
        when(userService.getUserTasks(1)).thenReturn(tasks);

        // Act
        var response = userController.getUserTasks(1);

        // Assert
        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getBody()).isNotNull().hasSize(2).containsExactlyElementsOf(tasks);
        verify(userService, times(1)).getUserTasks(1);
    }

    @Test
    void deleteUserById_shouldCallServiceAndReturnNoContent() {
        // Arrange
        doNothing().when(userService).deleteById(1);

        // Act
        var response = userController.deleteUserById(1);

        // Assert
        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.NO_CONTENT.value());
        assertThat(response.getBody()).isNull();
        verify(userService, times(1)).deleteById(1);
    }
}
