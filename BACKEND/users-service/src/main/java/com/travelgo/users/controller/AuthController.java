package com.travelgo.users.controller;

import com.travelgo.users.model.LoginRequest;
import com.travelgo.users.model.LoginResponse;
import com.travelgo.users.model.UserDto;
import com.travelgo.users.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    /**
     * AUTH ENDPOINTS
     *
     * POST   /user/login
     * GET    /user/me
     */

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        LoginResponse resp = userService.login(request);
        return ResponseEntity.ok(resp);
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> me() {
        UserDto u = userService.getFirstUser();
        if (u == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(u);
    }
}
