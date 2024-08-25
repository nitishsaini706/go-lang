package com.example.demo;

import com.example.demo.model.Task;
import com.example.demo.repository.TaskRepository;
import com.example.demo.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SpringBootApplication
@RestController
@RequestMapping("/api/tasks")
public class DemoApplication {

    @Autowired
    private TaskService taskService;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    // RESTful API endpoints for managing tasks
    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        Task task = taskService.getTaskById(id);
        if (task == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(task);
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        Task savedTask = taskService.createTask(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTask);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task task) {
        Task updatedTask = taskService.updateTask(id, task);
        if (updatedTask == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedTask);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}


// Object-Oriented Programming (OOP):
// The Task class represents a task entity, demonstrating the use of classes and objects in Java.
// The TaskService and TaskRepository classes showcase the use of abstraction and encapsulation by providing a service layer and a data access layer.
// Spring Boot Basics:
// The @SpringBootApplication annotation is used to mark the main class as the entry point of the Spring Boot application.
// The @RestController and @RequestMapping annotations define the REST controller and the base URL for the API endpoints.
// Dependency Injection:
// The TaskService is injected into the DemoApplication class using the @Autowired annotation, demonstrating how Spring manages dependencies through its IoC container.
// RESTful Web Services:
// The DemoApplication class defines several RESTful API endpoints using Spring MVC annotations like @GetMapping, @PostMapping, @PutMapping, and @DeleteMapping.
// These endpoints handle HTTP requests and responses for managing tasks.
// Data Persistence:
// The TaskRepository interface extends the JpaRepository interface provided by Spring Data JPA, enabling interaction with a relational database.
// The TaskService class uses the TaskRepository to perform CRUD (Create, Read, Update, Delete) operations on tasks.
// Error Handling:
// The code demonstrates how to handle exceptions by returning appropriate HTTP status codes, such as 404 Not Found when a task is not found.
// Configuration Management:
// The application can be configured using application properties or environment variables, allowing for different configurations based on profiles (e.g., development, production).
// Testing:
// The application can be tested using Spring Boot's testing features, such as MockMvc for testing REST endpoints and @DataJpaTest for testing data persistence.
// Security:
// Spring Security can be used to implement basic authentication and authorization for the API endpoints, ensuring secure access to the application.
// Actuator:
// Spring Boot Actuator can be used to monitor and manage the application, providing endpoints for health checks, metrics, and other operational information.
