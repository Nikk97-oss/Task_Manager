package dev.zanda.taskmanagerapi.repositories;
import dev.zanda.taskmanagerapi.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    //CRUD
    List<Task> getTaskByTitle(String title);


}
