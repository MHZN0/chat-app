package com.example.chatapp.Controller;

import com.example.chatapp.Model.User;
import com.example.chatapp.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        if (userRepo.existsByUsername(user.getUsername())) {
            return ResponseEntity.badRequest().body("Username already taken!");
        }
        if (userRepo.existsByEmail(user.getEmail())) {
            return ResponseEntity.badRequest().body("Email already used!");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepo.save(user);

        return ResponseEntity.ok(savedUser);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginReq) {
        Optional<User> userOpt = userRepo.findByUsername(loginReq.getUsername());

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (passwordEncoder.matches(loginReq.getPassword(), user.getPassword())) {
                return ResponseEntity.ok(user); // later, return JWT token instead
            }
        }
        return ResponseEntity.status(401).body("Invalid credentials");
    }
}
