package dev.zanda.taskmanagerapi.services;

import dev.zanda.taskmanagerapi.dto.TaskCreateRequest;
import dev.zanda.taskmanagerapi.dto.TaskResponse;
import dev.zanda.taskmanagerapi.dto.TaskUpdateRequest;
import dev.zanda.taskmanagerapi.exceptions.ResourceNotFoundException;
import dev.zanda.taskmanagerapi.exceptions.UnauthorizedAccessException;
import dev.zanda.taskmanagerapi.models.Task;
import dev.zanda.taskmanagerapi.models.User;
import dev.zanda.taskmanagerapi.models.enums.Status;
import dev.zanda.taskmanagerapi.repositories.TaskRepository;
import dev.zanda.taskmanagerapi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
public class TaskService {


    private final TaskRepository taskRepository;
    private final UserRepository userRepository;


    //Constructor injection
    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }


    public Page<TaskResponse> getAllTasks(Pageable pageable) {
        User currentUser= getUser();
        if (currentUser==null){
            throw new UnauthorizedAccessException("User Not Found");
        }
        return taskRepository.findByUser(currentUser,pageable).map(TaskResponse::fromEntity);
    }

    public List<TaskResponse> getTaskByTitle(String title) {

        User currentUser= getUser();
        if (currentUser==null){
            throw new UnauthorizedAccessException("User Not Found");
        }

        List<Task> tasks= taskRepository.getTaskByTitle(currentUser,title);
        if(tasks.isEmpty()){
            throw new ResourceNotFoundException("Task Not Found");
        }
        return tasks.stream().map(TaskResponse::fromEntity).toList();
    }


    public TaskResponse createTask(TaskCreateRequest taskCreateRequest) {
        Task task = new Task(
                taskCreateRequest.getTitle(),
                taskCreateRequest.getDescription(),
                taskCreateRequest.getPriority(),
                taskCreateRequest.getDueDate()
        );

        User currentUser= getUser();
        if (currentUser==null){
            throw new UnauthorizedAccessException("User Not Found");
        }

        task.setUser(currentUser);


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
        User currentUser= getUser();
        if (currentUser==null){
            throw new UnauthorizedAccessException("User Not Found");
        }

        Task existing =taskRepository.findById(id,currentUser)
                .orElseThrow(() -> new ResourceNotFoundException("Task with id " + id + " not found"));

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
        User currentUser= getUser();
        if (currentUser==null){
            throw new UnauthorizedAccessException("User Not Found");
        }
        Task task=taskRepository.findById(id,currentUser)
                .orElseThrow(() -> new ResourceNotFoundException("Task Not Found"));
        taskRepository.delete(task);
    }


    public User getUser(){
        String userName= ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();

        return userRepository.findByUsername(userName);
    }

}
