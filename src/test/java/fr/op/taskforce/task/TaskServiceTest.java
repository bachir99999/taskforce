package fr.op.taskforce.task;

import fr.op.taskforce.task.dto.TaskDTO;
import fr.op.taskforce.task.dto.TaskResponseDTO;
import fr.op.taskforce.task.entity.Task;
import fr.op.taskforce.user.UserRepository;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TaskMapper taskMapper;

    @InjectMocks
    private TaskService taskService;

    private TaskDTO taskDTO;
    private Task task;
    private TaskResponseDTO taskResponseDTO;
    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1);
        user.setName("John Doe");

        var userResponseDTO = new UserResponseDTO(1, "John Doe", "john@doe.com");

        taskDTO = new TaskDTO("Task 1", "Description 1", LocalDate.now(), Task.Status.TODO, 1);
        task = new Task("Task 1", "Description 1", LocalDate.now(), Task.Status.TODO, user);
        task.setId(1);
        taskResponseDTO = new TaskResponseDTO(1, "Task 1", "Description 1", LocalDate.now(), Task.Status.TODO, userResponseDTO);
    }

    @Test
    void getAllTasks_shouldReturnTaskList() {
        when(taskRepository.findAll()).thenReturn(List.of(task));
        when(taskMapper.taskListToTaskResponseDTOList(anyList())).thenReturn(List.of(taskResponseDTO));

        var result = taskService.getAllTasks();

        assertEquals(1, result.size());
        assertEquals("Task 1", result.getFirst().name());
    }

    @Test
    void getTaskById_shouldReturnTask_whenFound() {
        when(taskRepository.findById(1)).thenReturn(Optional.of(task));
        when(taskMapper.taskToTaskResponseDTO(task)).thenReturn(taskResponseDTO);

        var result = taskService.getTaskById(1);

        assertNotNull(result);
        assertEquals("Task 1", result.name());
    }

    @Test
    void getTaskById_shouldThrowException_whenNotFound() {
        when(taskRepository.findById(1)).thenReturn(Optional.empty());

        var exception = assertThrows(RuntimeException.class, () -> taskService.getTaskById(1));
        assertEquals("Task not found with id 1", exception.getMessage());
    }

    @Test
    void saveTask_shouldReturnSavedTask() {
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(taskMapper.taskDTOToTask(taskDTO, user)).thenReturn(task);
        when(taskRepository.save(task)).thenReturn(task);
        when(taskMapper.taskToTaskResponseDTO(task)).thenReturn(taskResponseDTO);

        var result = taskService.saveTask(taskDTO);

        assertNotNull(result);
        assertEquals("Task 1", result.name());
    }

    @Test
    void updateTask_shouldReturnUpdatedTask() {
        when(taskRepository.findById(1)).thenReturn(Optional.of(task));
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(taskRepository.save(any(Task.class))).thenReturn(task);
        when(taskMapper.taskToTaskResponseDTO(any(Task.class))).thenReturn(taskResponseDTO);

        var result = taskService.updateTask(1, taskDTO);

        assertNotNull(result);
        assertEquals("Task 1", result.name());
    }

    @Test
    void deleteTask_shouldDeleteTask_whenFound() {
        when(taskRepository.existsById(1)).thenReturn(true);
        doNothing().when(taskRepository).deleteById(1);

        assertDoesNotThrow(() -> taskService.deleteTask(1));

        verify(taskRepository, times(1)).deleteById(1);
    }

    @Test
    void deleteTask_shouldThrowException_whenNotFound() {
        when(taskRepository.existsById(1)).thenReturn(false);

        var exception = assertThrows(RuntimeException.class, () -> taskService.deleteTask(1));
        assertEquals("Task not found with id 1", exception.getMessage());
    }
}
