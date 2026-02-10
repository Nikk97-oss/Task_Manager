package dev.zanda.taskmanagerapi.services;

import dev.zanda.taskmanagerapi.dto.TaskCreateRequest;
import dev.zanda.taskmanagerapi.dto.TaskResponse;
import dev.zanda.taskmanagerapi.dto.TaskUpdateRequest;
import dev.zanda.taskmanagerapi.models.Task;
import dev.zanda.taskmanagerapi.models.enums.Status;
import dev.zanda.taskmanagerapi.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
public class TaskService {


    private final TaskRepository taskRepository;


    //Constructor injection
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Page<TaskResponse> getAllTasks(Pageable pageable) {
        return taskRepository.findAll(pageable).map(TaskResponse::fromEntity);

    }

    public List<TaskResponse> getTaskByTitle(String title) {
        List<Task> tasks= taskRepository.getTaskByTitle(title);
        if(tasks.isEmpty()){
            throw new RuntimeException("Task Not Found");
        }
        return tasks.stream().map(TaskResponse::fromEntity).toList();
    }


    public TaskResponse createTask(TaskCreateRequest taskCreateRequest) {
        Task task = new Task(
                taskCreateRequest.getTitle(),
                taskCreateRequest.getDescription(),
                Status.TODO,
                taskCreateRequest.getPriority(),
                taskCreateRequest.getDueDate(),
                null,null //Stesso motivo di status
        );



        Task createdTask = taskRepository.save(task);

        return new TaskResponse(
                createdTask.getId(),
                createdTask.getTitle(),
                createdTask.getDescription(),
                createdTask.getStatus(),
                createdTask.getPriority(),
                createdTask.getDueDate(),
                createdTask.getCreatedDate(),
                createdTask.getUpdatedDate()
        );
    }


    public TaskResponse updateTask(long id, TaskUpdateRequest taskUpdateRequest) {
        Task existing =taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task with id " + id + " not found"));

        if(taskUpdateRequest.getTitle() != null)
            existing.setTitle(taskUpdateRequest.getTitle());

        if(taskUpdateRequest.getDescription() != null)
            existing.setDescription(taskUpdateRequest.getDescription());

        if(taskUpdateRequest.getStatus() != null)
            existing.setStatus(taskUpdateRequest.getStatus());

        if(taskUpdateRequest.getPriority() != null)
            existing.setPriority(taskUpdateRequest.getPriority());

        if(taskUpdateRequest.getDueDate() != null)
            existing.setDueDate(taskUpdateRequest.getDueDate());




        Task saved=taskRepository.save(existing);
        return new TaskResponse(
                saved.getId(),
                saved.getTitle(),
                saved.getDescription(),
                saved.getStatus(),
                saved.getPriority(),
                saved.getDueDate(),
                saved.getCreatedDate(),
                saved.getUpdatedDate()
        );
    }


    public void deleteTask(long id) {
        Task task=taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task Not Found"));
        taskRepository.delete(task);
    }


}
