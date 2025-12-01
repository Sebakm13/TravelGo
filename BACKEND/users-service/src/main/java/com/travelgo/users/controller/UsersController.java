package com.travelgo.users.controller;

import com.travelgo.users.model.UserDto;
import com.travelgo.users.model.UsersResponse;
import com.travelgo.users.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UsersController {

    private final UserService userService;

    public UsersController(UserService userService) {
        this.userService = userService;
    }

    /**
     * USERS SERVICE – ENDPOINTS
     *
     * GET    /users
     * POST   /users
     * GET    /users/{id}
     * PUT    /users/{id}
     * DELETE /users/{id}
     */

    @GetMapping("/users")
    public UsersResponse getUsers(
            @RequestParam(value = "q", required = false) String q,
            @RequestParam(value = "skip", required = false) Integer skip,
            @RequestParam(value = "limit", required = false) Integer limit
    ) {
        return userService.getAll(q, skip, limit);
    }

    @GetMapping("/users/search")
    public UsersResponse searchUsers(
            @RequestParam(value = "q", required = false) String q,
            @RequestParam(value = "skip", required = false) Integer skip,
            @RequestParam(value = "limit", required = false) Integer limit
    ) {
        return userService.getAll(q, skip, limit);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable("id") Integer id) {
        UserDto dto = userService.getById(id);
        if (dto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/users")
    public ResponseEntity<UserDto> create(@RequestBody UserDto user) {
        UserDto created = userService.create(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<UserDto> update(@PathVariable("id") Integer id, @RequestBody UserDto user) {
        UserDto updated = userService.update(id, user);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
