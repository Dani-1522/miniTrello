package com.practica.miniTrello.contotroller;

import com.practica.miniTrello.DTOs.AuthRequest;
import com.practica.miniTrello.DTOs.AuthResponse;
import com.practica.miniTrello.DTOs.RegisterRequest;
import com.practica.miniTrello.entity.Role;
import com.practica.miniTrello.entity.User;
import com.practica.miniTrello.security.JwtService;
import com.practica.miniTrello.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")

public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;

    public AuthController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest req) {
        User user = User.builder()
                .username(req.getUsername())
                .email(req.getEmail())
                .password(req.getPassword())
                .role(Role.USER)
                .build();

        User saved = userService.CrearUsuario(user);
        String token = jwtService.generateToken(saved);

        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest req) {
        User user = userService.getByUsername(req.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("No user"));

        if (!userService.checkPassword(req.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String token = jwtService.generateToken(user);
        return ResponseEntity.ok(new AuthResponse(token));
    }
}
