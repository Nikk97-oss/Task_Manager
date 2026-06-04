package dev.zanda.taskmanagerapi.repositories;
import dev.zanda.taskmanagerapi.models.Task;
import dev.zanda.taskmanagerapi.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {
    //CRUD Repository
    List<Task> getTaskByTitle(User user,String title);

    Page<Task> findByUser(User user, Pageable pageable);

    Optional<Task> findById(long id, User user);

}
