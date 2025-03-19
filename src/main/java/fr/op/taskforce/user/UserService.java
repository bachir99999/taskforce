package fr.op.taskforce.user;

import fr.op.taskforce.task.TaskMapper;
import fr.op.taskforce.task.dto.TaskResponseDTO;
import fr.op.taskforce.user.dto.UserDTO;
import fr.op.taskforce.user.dto.UserResponseDTO;
import fr.op.taskforce.user.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final TaskMapper taskMapper;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.userMapper = new UserMapper();
        this.taskMapper = new TaskMapper();
    }

    public UserResponseDTO save(UserDTO userDTO) {
        return userMapper.userToResponseDTO(userRepository.save(userMapper.userDTOToUser(userDTO)));
    }

    public List<UserResponseDTO> saveAll(List<UserDTO> userDTOList) {
        return  userMapper.usersToResponseDTOs(userRepository.saveAll(userMapper.usersDTOToUsers(userDTOList)));
    }

    public UserResponseDTO findById(Integer id) {
        return userMapper.userToResponseDTO(userRepository.findById(id).orElse(null));
    }

    public List<UserResponseDTO> findAll() {
        return userMapper.usersToResponseDTOs(userRepository.findAll());
    }

    public void deleteById(Integer id) {
        userRepository.deleteById(id);
    }

    public void deleteAllByIds(List<Integer> ids){
        userRepository.deleteAllById(ids);
    }

    public UserResponseDTO update(Integer id, UserDTO updatedUser) {
        var existingUser = userRepository.findById(id).orElse(new User());
        existingUser.setName(updatedUser.name());
        existingUser.setEmail(updatedUser.email());
        existingUser.setPassword(updatedUser.password());
        return userMapper.userToResponseDTO(userRepository.save(existingUser));
    }

    public UserResponseDTO partialUpdate(Integer id, UserDTO partialUser) {
        var existingUser = userRepository.findById(id).orElse(null);
        if (existingUser == null) return null;
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
}
