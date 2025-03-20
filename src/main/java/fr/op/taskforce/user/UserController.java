package fr.op.taskforce.user;

import fr.op.taskforce.task.dto.TaskResponseDTO;
import fr.op.taskforce.user.dto.UserDTO;
import fr.op.taskforce.user.dto.UserResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserDTO userDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(userDTO));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<UserResponseDTO>> createUsers(@Valid @RequestBody List<UserDTO> usersDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveAll(usersDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Integer id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @GetMapping("/name/{username}")
    public ResponseEntity<UserResponseDTO> getUserByUsername(@PathVariable String username) {
        return ResponseEntity.ok(userService.findByName(username));
    }

    @GetMapping("/{id}/tasks")
    public ResponseEntity<List<TaskResponseDTO>> getUserTasks(@PathVariable Integer id) {
        return ResponseEntity.ok(userService.getUserTasks(id));
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Integer id, @Valid @RequestBody UserDTO updatedUser) {
        return ResponseEntity.ok(userService.update(id, updatedUser));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserResponseDTO> partialUpdateUser(@PathVariable Integer id, @Valid @RequestBody UserDTO partialUser) {
        return ResponseEntity.ok(userService.partialUpdate(id, partialUser));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Integer id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/batch")
    public ResponseEntity<Void> deleteUsers(@RequestBody List<Integer> ids) {
        userService.deleteAllByIds(ids);
        return ResponseEntity.noContent().build();
    }
}
