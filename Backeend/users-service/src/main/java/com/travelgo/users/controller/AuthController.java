package com.travelgo.users.controller;

import com.travelgo.users.model.LoginRequest;
import com.travelgo.users.model.LoginResponse;
import com.travelgo.users.model.UserDto;
import com.travelgo.users.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class AuthController {
    private final UserRepository userRepository;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        // lógica muy básica: siempre loguea al primer usuario demo
        UserDto u = userRepository.findAll().stream().findFirst().orElse(null);
        if (u == null) {
            return ResponseEntity.badRequest().build();
        }

        LoginResponse resp = new LoginResponse();
        resp.setId(u.getId());
        resp.setUsername(u.getUsername());
        resp.setEmail(u.getEmail());
        resp.setFirstName(u.getFirstName());
        resp.setLastName(u.getLastName());
        resp.setImage(u.getImage());
        return ResponseEntity.ok(resp);
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> me() {
        UserDto u = userRepository.findAll().stream().findFirst().orElse(null);
        if (u == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(u);
    }
}
