package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The main entry point of the Spring Boot application.
 * This class is marked with the @SpringBootApplication annotation,
 * which enables auto-configuration, component scanning, and property support.
 */
@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        // Launch the Spring Boot application
        SpringApplication.run(DemoApplication.class, args);
    }
}


package com.example.demo.controller;

import com.example.demo.model.Task;
import com.example.demo.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing tasks.
 * This class handles incoming HTTP requests and maps them to appropriate service methods.
 */
@RestController
@RequestMapping("/api/tasks") // Base URL for all task-related endpoints
public class TaskController {

    @Autowired
    private TaskService taskService; // Injecting the TaskService using Spring's Dependency Injection

    /**
     * Retrieve all tasks from the database.
     * @return List of tasks
     */
    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.getAllTasks(); // Call service method to get all tasks
    }

    /**
     * Retrieve a specific task by its ID.
     * @param id the ID of the task
     * @return ResponseEntity containing the task or 404 if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        Task task = taskService.getTaskById(id);
        if (task == null) {
            return ResponseEntity.notFound().build(); // Return 404 if task not found
        }
        return ResponseEntity.ok(task); // Return 200 with the task
    }

    /**
     * Create a new task.
     * @param task the task to create
     * @return ResponseEntity containing the created task and 201 status
     */
    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        Task savedTask = taskService.createTask(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTask); // Return 201 with the created task
    }

    /**
     * Update an existing task.
     * @param id the ID of the task to update
     * @param task the updated task data
     * @return ResponseEntity containing the updated task or 404 if not found
     */
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task task) {
        Task updatedTask = taskService.updateTask(id, task);
        if (updatedTask == null) {
            return ResponseEntity.notFound().build(); // Return 404 if task not found
        }
        return ResponseEntity.ok(updatedTask); // Return 200 with the updated task
    }

    /**
     * Delete a task by its ID.
     * @param id the ID of the task to delete
     * @return ResponseEntity with 204 status
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id); // Call service method to delete the task
        return ResponseEntity.noContent().build(); // Return 204 No Content
    }
}


package com.example.demo.model;

import javax.persistence.*;

/**
 * Entity class representing a Task.
 * This class is mapped to a database table using JPA annotations.
 */
@Entity // Indicates that this class is a JPA entity
@Table(name = "tasks") // Specifies the name of the table in the database
public class Task {

    @Id // Specifies the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-generate the ID
    private Long id;

    private String title; // Title of the task
    private String description; // Description of the task
    private boolean completed; // Status of the task (completed or not)

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}


package com.example.demo.repository;

import com.example.demo.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for accessing Task data.
 * This interface extends JpaRepository to provide CRUD operations.
 */
@Repository // Indicates that this interface is a Spring Data repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    // No additional methods are needed; JpaRepository provides basic CRUD operations
}


package com.example.demo.service;

import com.example.demo.model.Task;
import com.example.demo.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing tasks.
 * This class contains business logic and interacts with the repository.
 */
@Service // Indicates that this class is a service component
public class TaskService {

    @Autowired
    private TaskRepository taskRepository; // Injecting the TaskRepository

    /**
     * Retrieve all tasks from the repository.
     * @return List of tasks
     */
    public List<Task> getAllTasks() {
        return taskRepository.findAll(); // Fetch all tasks from the database
    }

    /**
     * Retrieve a task by its ID.
     * @param id the ID of the task
     * @return the task if found, otherwise null
     */
    public Task getTaskById(Long id) {
        Optional<Task> task = taskRepository.findById(id); // Find task by ID
        return task.orElse(null); // Return the task or null if not found
    }

    /**
     * Create a new task.
     * @param task the task to create
     * @return the created task
     */
    public Task createTask(Task task) {
        return taskRepository.save(task); // Save the task to the database
    }

    /**
     * Update an existing task.
     * @param id the ID of the task to update
     * @param task the updated task data
     * @return the updated task if found, otherwise null
     */
    public Task updateTask(Long id, Task task) {
        if (!taskRepository.existsById(id)) {
            return null; // Return null if the task does not exist
        }
        task.setId(id); // Set the ID for the task to be updated
        return taskRepository.save(task); // Save the updated task
    }

    /**
     * Delete a task by its ID.
     * @param id the ID of the task to delete
     */
    public void deleteTask(Long id) {
        taskRepository.deleteById(id); // Delete the task from the database
    }
}


# Application properties for configuring the Spring Boot application

# Database configuration (H2 in-memory database for simplicity)
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

# JPA configuration
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.h2.console.enabled=true # Enable H2 console for testing

# Server configuration
server.port=8080 # Port on which the application will run
