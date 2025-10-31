package com.example.microservicio_usuarios.controller;

import com.example.microservicio_usuarios.model.User;
import com.example.microservicio_usuarios.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository repo;

    public UserController(UserRepository repo) {
        this.repo = repo;
    }

    // Obtener todos los usuarios
    @GetMapping
    public List<User> getAll() {
        return repo.findAll();
    }

    // Obtener usuario por ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable Long id) {
        return repo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Crear nuevo usuario
    @PostMapping
    public User create(@RequestBody User u) {
        return repo.save(u);
    }

    // Actualizar usuario existente
    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User u) {
        return repo.findById(id)
                .map(db -> {
                    db.setUsername(u.getUsername());
                    db.setPassword(u.getPassword());
                    db.setRole(u.getRole());
                    return ResponseEntity.ok(repo.save(db));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Eliminar usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!repo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Login básico (solo para demostrar funcionalidad)
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User u) {
        return repo.findByUsername(u.getUsername())
                .filter(db -> db.getPassword().equals(u.getPassword()))
                .map(db -> ResponseEntity.ok("OK_ROLE=" + db.getRole()))
                .orElse(ResponseEntity.status(401).body("Credenciales inválidas"));
    }
}
