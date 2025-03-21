package fr.op.taskforce.task;

import fr.op.taskforce.task.dto.TaskDTO;
import fr.op.taskforce.task.dto.TaskResponseDTO;
import fr.op.taskforce.task.entity.Task;
import fr.op.taskforce.user.dto.UserResponseDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskControllerTest {

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    @Test
    void getAllTasks_shouldReturnListOfTasks() {
        var userResponseDTO = new UserResponseDTO(1, "John Doe", "john@doe.com");
        var taskResponseDTO = new TaskResponseDTO(1, "Task 1", "Description", LocalDate.now(), Task.Status.TODO, userResponseDTO);
        when(taskService.getAllTasks()).thenReturn(List.of(taskResponseDTO));

        var response = taskController.getAllTasks();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void getTaskById_shouldReturnTask_whenFound() {
        var userResponseDTO = new UserResponseDTO(1, "John Doe", "john@doe.com");
        var taskResponseDTO = new TaskResponseDTO(1, "Task 1", "Description", LocalDate.now(), Task.Status.TODO, userResponseDTO);
        when(taskService.getTaskById(1)).thenReturn(taskResponseDTO);

        var response = taskController.getTaskById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Task 1", response.getBody().name());
    }

    @Test
    void createTask_shouldReturnCreatedTask() {
        var taskDTO = new TaskDTO("Task 1", "Description", LocalDate.now(), Task.Status.TODO, 1);
        var userResponseDTO = new UserResponseDTO(1, "John Doe", "john@doe.com");
        var taskResponseDTO = new TaskResponseDTO(1, "Task 1", "Description", LocalDate.now(), Task.Status.TODO, userResponseDTO);
        when(taskService.saveTask(taskDTO)).thenReturn(taskResponseDTO);

        var response = taskController.createTask(taskDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Task 1", response.getBody().name());
    }

    @Test
    void updateTask_shouldReturnUpdatedTask() {
        var taskDTO = new TaskDTO("Updated Task", "Updated Description", LocalDate.now(), Task.Status.IN_PROGRESS, 1);
        var userResponseDTO = new UserResponseDTO(1, "John Doe", "john@doe.com");
        var taskResponseDTO = new TaskResponseDTO(1, "Updated Task", "Updated Description", LocalDate.now(), Task.Status.IN_PROGRESS, userResponseDTO);
        when(taskService.updateTask(1, taskDTO)).thenReturn(taskResponseDTO);

        var response = taskController.updateTask(1, taskDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Updated Task", response.getBody().name());
    }

    @Test
    void patchTask_shouldReturnPatchedTask() {
        var taskDTO = new TaskDTO("Patched Task", null, null, Task.Status.DONE, null);
        var userResponseDTO = new UserResponseDTO(1, "John Doe", "john@doe.com");
        var taskResponseDTO = new TaskResponseDTO(1, "Patched Task", "Description", LocalDate.now(), Task.Status.DONE, userResponseDTO);
        when(taskService.patchTask(1, taskDTO)).thenReturn(taskResponseDTO);

        var response = taskController.patchTask(1, taskDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Patched Task", response.getBody().name());
    }

    @Test
    void deleteTask_shouldReturnNoContent() {
        doNothing().when(taskService).deleteTask(1);

        var response = taskController.deleteTask(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(taskService, times(1)).deleteTask(1);
    }
}
