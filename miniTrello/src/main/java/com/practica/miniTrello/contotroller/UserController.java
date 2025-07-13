package com.practica.miniTrello.contotroller;

import com.practica.miniTrello.entity.User;
import com.practica.miniTrello.service.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")

public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> crearUsuario(@RequestBody User user) {
        return ResponseEntity.ok(userService.CrearUsuario(user));
    }
    @GetMapping
    public ResponseEntity<List<User>> listarUsuarios() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> usuarioPorId(@PathVariable Long id) {
        return userService.UsuarioPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> eliminarUsuario(@PathVariable Long id) {
         userService.eliminarUsuario(id);
         return ResponseEntity.ok().build();
    }
}
