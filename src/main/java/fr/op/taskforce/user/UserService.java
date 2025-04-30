package fr.op.taskforce.user;

import fr.op.taskforce.config.JWTService;
import fr.op.taskforce.task.TaskMapper;
import fr.op.taskforce.task.dto.TaskResponseDTO;
import fr.op.taskforce.user.dto.UserDTO;
import fr.op.taskforce.user.dto.UserLoginDTO;
import fr.op.taskforce.user.dto.UserResponseDTO;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final TaskMapper taskMapper;
    private final BCryptPasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    public UserService(UserRepository userRepository, UserMapper userMapper, TaskMapper taskMapper, AuthenticationManager authenticationManager, BCryptPasswordEncoder bCryptPasswordEncoder, JWTService jwtService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.taskMapper = taskMapper;
        this.encoder = bCryptPasswordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }


    public UserResponseDTO save(UserDTO userDTO) {
        var user = userMapper.userDTOToUser(userDTO);
        user.setPassword(encoder.encode(user.getPassword()));
        return userMapper.userToResponseDTO(userRepository.save(user));
    }

    public List<UserResponseDTO> saveAll(List<UserDTO> userDTOList) {
        return userMapper.usersToResponseDTOs(userRepository.saveAll(userMapper.usersDTOToUsers(userDTOList)));
    }

    public UserResponseDTO findById(Integer id) {
        return userRepository.findById(id)
                .map(userMapper::userToResponseDTO)
                .orElseThrow(() -> new RuntimeException("User not found with id " + id));
    }

    public List<UserResponseDTO> findAll() {
        return userMapper.usersToResponseDTOs(userRepository.findAll());
    }

    public void deleteById(Integer id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found with id " + id);
        }
        userRepository.deleteById(id);
    }

    public void deleteAllByIds(List<Integer> ids) {
        userRepository.deleteAllById(ids);
    }

    public UserResponseDTO update(Integer id, UserDTO updatedUser) {
        var existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id " + id));

        existingUser.setName(updatedUser.name());
        existingUser.setEmail(updatedUser.email());
        existingUser.setPassword(updatedUser.password());
        return userMapper.userToResponseDTO(userRepository.save(existingUser));
    }

    public UserResponseDTO partialUpdate(Integer id, UserDTO partialUser) {
        var existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id " + id));

        if (partialUser.name() != null) existingUser.setName(partialUser.name());
        if (partialUser.email() != null) existingUser.setEmail(partialUser.email());
        if (partialUser.password() != null) existingUser.setPassword(partialUser.password());
        return userMapper.userToResponseDTO(userRepository.save(existingUser));
    }


    public UserResponseDTO findByName(String username) {
        return userMapper.userToResponseDTO(userRepository.findByName(username));
    }

    public List<TaskResponseDTO> getUserTasks(Integer userId) {
        var user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return taskMapper.taskListToTaskResponseDTOList(user.getTaskList());
    }

    public String verify(UserLoginDTO userLginDTO) {
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userLginDTO.name(), userLginDTO.password())
        );

        if (!auth.isAuthenticated()) {
            throw new BadCredentialsException("Authentication failed for user: " + userLginDTO.name());
        }

        return jwtService.generateToken(userLginDTO.name());
    }
}
