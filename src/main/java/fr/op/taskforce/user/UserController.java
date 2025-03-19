package fr.op.taskforce.user;

import fr.op.taskforce.task.dto.TaskResponseDTO;
import fr.op.taskforce.user.dto.UserDTO;
import fr.op.taskforce.user.dto.UserResponseDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public UserResponseDTO createUser(@RequestBody UserDTO userDTO) {
        return userService.save(userDTO);
    }


    @PostMapping("/batch")
    public List<UserResponseDTO> createUsers(@RequestBody List<UserDTO> usersDTO) {
        return userService.saveAll(usersDTO);
    }

    @GetMapping("/{id}")
    public UserResponseDTO getUserById(@PathVariable Integer id) {
        return userService.findById(id);
    }

    @GetMapping("/name/{username}")
    public UserResponseDTO getUserByUsername(@PathVariable String username) {
        return userService.findByName(username);
    }

    @GetMapping("/{id}/tasks")
    public List<TaskResponseDTO> getUserTasks(@PathVariable Integer id) {
        return userService.getUserTasks(id);
    }


    @GetMapping
    public List<UserResponseDTO> getAllUsers() {
        return userService.findAll();
    }

    @PutMapping("/{id}")
    public UserResponseDTO updateUser(@PathVariable Integer id, @RequestBody UserDTO updatedUser) {
        return userService.update(id, updatedUser);
    }

    @PatchMapping("/{id}")
    public UserResponseDTO partialUpdateUser(@PathVariable Integer id, @RequestBody UserDTO partialUser) {
        return userService.partialUpdate(id, partialUser);
    }


    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable Integer id) {
        userService.deleteById(id);
    }

    @DeleteMapping("/batch")
    public void deleteUsers(@RequestBody List<Integer> ids) {
        userService.deleteAllByIds(ids);
    }
}
