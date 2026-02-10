package dev.zanda.taskmanagerapi.dto;

import dev.zanda.taskmanagerapi.models.enums.Priority;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class TaskCreateRequest {

    @NotBlank
    @Size(max=100)
    private String title;

    @Size(max=500)
    private String description;

    private Priority priority;

    @FutureOrPresent(message="La data di scadenza non può essere passata")
    private LocalDate dueDate;

    public TaskCreateRequest() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
}
