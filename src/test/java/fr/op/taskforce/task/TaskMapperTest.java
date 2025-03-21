package fr.op.taskforce.task;

import fr.op.taskforce.task.dto.TaskDTO;
import fr.op.taskforce.task.dto.TaskResponseDTO;
import fr.op.taskforce.task.entity.Task;
import fr.op.taskforce.task.entity.Task.Status;
import fr.op.taskforce.user.UserMapper;
import fr.op.taskforce.user.dto.UserResponseDTO;
import fr.op.taskforce.user.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskMapperTest {

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private TaskMapper taskMapper;

    private User user;
    private UserResponseDTO userResponseDTO;
    private Task task;
    private TaskDTO taskDTO;

    @BeforeEach
    void setUp() {
        user = new User("John Doe", "johndoe@example.com", "password");
        user.setId(1);
        userResponseDTO = new UserResponseDTO(1, "John Doe", "johndoe@example.com");

        task = new Task("Task 1", "Description 1", LocalDate.of(2025, 3, 21), Status.IN_PROGRESS, user);
        task.setId(1);
        taskDTO = new TaskDTO("Task 1", "Description 1", LocalDate.of(2025, 3, 21), Status.IN_PROGRESS, 1);
        var taskResponseDTO = new TaskResponseDTO(1, "Task 1", "Description 1", LocalDate.of(2025, 3, 21), Status.IN_PROGRESS, userResponseDTO);
    }

    @Test
    void taskToTaskResponseDTO_shouldConvertTaskToDTO() {
        // Arrange
        when(userMapper.userToResponseDTO(user)).thenReturn(userResponseDTO);

        // Act
        var result = taskMapper.taskToTaskResponseDTO(task);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(task.getId());
        assertThat(result.name()).isEqualTo(task.getName());
        assertThat(result.description()).isEqualTo(task.getDescription());
        assertThat(result.dueDate()).isEqualTo(task.getDueDate());
        assertThat(result.status()).isEqualTo(task.getStatus());
        assertThat(result.assignedTo()).isEqualTo(userResponseDTO);
        verify(userMapper, times(1)).userToResponseDTO(user);
    }

    @Test
    void taskToTaskResponseDTO_shouldReturnNullWhenTaskIsNull() {
        // Act
        var result = taskMapper.taskToTaskResponseDTO(null);

        // Assert
        assertThat(result).isNull();
    }

    @Test
    void taskDTOToTask_shouldConvertDTOToTask() {
        // Act
        var result = taskMapper.taskDTOToTask(taskDTO, user);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo(taskDTO.name());
        assertThat(result.getDescription()).isEqualTo(taskDTO.description());
        assertThat(result.getDueDate()).isEqualTo(taskDTO.dueDate());
        assertThat(result.getStatus()).isEqualTo(taskDTO.status());
        assertThat(result.getAssignedTo()).isEqualTo(user);
    }

    @Test
    void taskDTOToTask_shouldReturnNullWhenDTOIsNull() {
        // Act
        var result = taskMapper.taskDTOToTask(null, user);

        // Assert
        assertThat(result).isNull();
    }

    @Test
    void taskListToTaskResponseDTOList_shouldConvertTaskListToDTOList() {
        // Arrange
        var tasks = List.of(task);
        when(userMapper.userToResponseDTO(user)).thenReturn(userResponseDTO);

        // Act
        var result = taskMapper.taskListToTaskResponseDTOList(tasks);

        // Assert
        assertThat(result).hasSize(1);
        assertThat(result.get(0).id()).isEqualTo(task.getId());
        verify(userMapper, times(1)).userToResponseDTO(user);
    }

    @Test
    void taskListToTaskResponseDTOList_shouldReturnEmptyListWhenTasksAreEmpty() {
        // Act
        var result = taskMapper.taskListToTaskResponseDTOList(List.of());

        // Assert
        assertThat(result).isEmpty();
    }
}
