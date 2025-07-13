package com.practica.miniTrello.service;

import com.practica.miniTrello.entity.Role;
import com.practica.miniTrello.entity.User;
import com.practica.miniTrello.repository.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service

public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User CrearUsuario (User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);
        return userRepository.save(user);
    }

   public List<User> findAll() {
        return userRepository.findAll();
   }

   public Optional<User> UsuarioPorId(Long id) {
        return userRepository.findById(id);
   }

    public Optional<User> getByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public boolean checkPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    public void eliminarUsuario(Long id) {
        userRepository.deleteById(id);
   }
}
