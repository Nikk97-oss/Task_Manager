package dev.zanda.taskmanagerapi.dto;

import dev.zanda.taskmanagerapi.models.enums.Priority;
import dev.zanda.taskmanagerapi.models.enums.Status;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


import java.time.LocalDate;




public class TaskUpdateRequest {

    @NotNull
    private long id;

    @Size(max=100)
    private String title;

    @Size(max=500)
    private String description;
    private Status status;
    private Priority priority;

    @FutureOrPresent(message="La data di scadenza non può essere passata")
    private LocalDate dueDate;


    public TaskUpdateRequest() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
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
