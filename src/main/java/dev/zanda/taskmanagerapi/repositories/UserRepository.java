package dev.zanda.taskmanagerapi.repositories;

import dev.zanda.taskmanagerapi.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
}
