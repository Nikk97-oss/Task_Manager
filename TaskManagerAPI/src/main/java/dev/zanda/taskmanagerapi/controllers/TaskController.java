package dev.zanda.taskmanagerapi.controllers;

import dev.zanda.taskmanagerapi.dto.TaskCreateRequest;
import dev.zanda.taskmanagerapi.dto.TaskResponse;
import dev.zanda.taskmanagerapi.dto.TaskUpdateRequest;
import dev.zanda.taskmanagerapi.services.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/task")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/paged")
    public ResponseEntity<Page<TaskResponse>> getAllTasks(Pageable pageable) {
        return ResponseEntity.ok(taskService.getAllTasks(pageable));
    }

    @GetMapping("/search/{title}")
    public ResponseEntity<List<TaskResponse>> getAllTasksByTitle(@Valid @PathVariable String title) {
        return ResponseEntity.ok(taskService.getTaskByTitle(title));
    }

    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@Valid @RequestBody TaskCreateRequest taskCreateRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.createTask(taskCreateRequest));
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<TaskResponse> updateTask(@PathVariable long id, @Valid @RequestBody TaskUpdateRequest task){
        return ResponseEntity.ok(taskService.updateTask(id, task));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String,String>> deleteTask(@PathVariable long id){
        taskService.deleteTask(id);
        Map<String,String> response = Map.of("message","Task Deleted");
        return ResponseEntity.ok(response);
    }
}
