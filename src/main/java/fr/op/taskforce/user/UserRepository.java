package fr.op.taskforce.user;

import fr.op.taskforce.task.entity.Task;
import fr.op.taskforce.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByName(String name);
}
