package fr.op.taskforce.user;

import fr.op.taskforce.task.TaskMapper;
import fr.op.taskforce.user.dto.UserDTO;
import fr.op.taskforce.user.dto.UserResponseDTO;
import fr.op.taskforce.user.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private TaskMapper taskMapper;

    @InjectMocks
    private UserService userService;

    private User user;
    private UserDTO userDTO;
    private UserResponseDTO userResponseDTO;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1);
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");
        user.setPassword("password");

        userDTO = new UserDTO("John Doe", "john.doe@example.com", "password");
        userResponseDTO = new UserResponseDTO(1, "John Doe", "john.doe@example.com");
    }

    @Test
    void testSaveUser() {
        // Arrange
        when(userMapper.userDTOToUser(userDTO)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.userToResponseDTO(user)).thenReturn(userResponseDTO);

        // Act
        var result = userService.save(userDTO);

        // Assert
        assertNotNull(result);
        assertEquals(userResponseDTO, result);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testFindById_UserExists() {
        // Arrange
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(userMapper.userToResponseDTO(user)).thenReturn(userResponseDTO);

        // Act
        var result = userService.findById(1);

        // Assert
        assertNotNull(result);
        assertEquals(userResponseDTO, result);
    }

    @Test
    void testFindById_UserNotFound() {
        // Arrange
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        var thrown = assertThrows(RuntimeException.class, () -> userService.findById(1));
        assertEquals("User not found with id 1", thrown.getMessage());
    }

    @Test
    void testDeleteById_UserExists() {
        // Arrange
        when(userRepository.existsById(1)).thenReturn(true);
        doNothing().when(userRepository).deleteById(1);

        // Act
        userService.deleteById(1);

        // Assert
        verify(userRepository, times(1)).deleteById(1);
    }

    @Test
    void testDeleteById_UserNotFound() {
        // Arrange
        when(userRepository.existsById(1)).thenReturn(false);

        // Act & Assert
        var thrown = assertThrows(RuntimeException.class, () -> userService.deleteById(1));
        assertEquals("User not found with id 1", thrown.getMessage());
    }

    @Test
    void testUpdate_UserExists() {
        // Arrange
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.userToResponseDTO(user)).thenReturn(userResponseDTO);

        // Act
        var result = userService.update(1, userDTO);

        // Assert
        assertNotNull(result);
        assertEquals(userResponseDTO, result);
    }

    @Test
    void testUpdate_UserNotFound() {
        // Arrange
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        var thrown = assertThrows(RuntimeException.class, () -> userService.update(1, userDTO));
        assertEquals("User not found with id 1", thrown.getMessage());
    }
}
