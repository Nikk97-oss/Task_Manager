package dev.zanda.taskmanagerapi.models;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique=true, nullable=false)
    private String username;
    @Column(nullable=false)
    private String password;

    @OneToMany(mappedBy ="user", cascade = CascadeType.ALL, orphanRemoval = true, fetch=FetchType.LAZY)
    private List<Task> tasks= new ArrayList<>();

    public User() {
    }

    public User(String username, String password, List<Task> tasks) {
        this.username = username;
        this.password = password;
        this.tasks = tasks;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Task> getTaks() {
        return tasks;
    }

    public void setTaks(List<Task> taks) {
        this.tasks = tasks;
    }

    public void addTask(Task task){
        tasks.add(task);
        task.setUser(this);
    }

    public void removeTask(Task task){
        tasks.remove(task);
        task.setUser(null);
    }
}

