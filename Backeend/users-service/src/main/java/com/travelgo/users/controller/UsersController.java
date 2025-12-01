package com.travelgo.users.controller;

import com.travelgo.users.model.UserDto;
import com.travelgo.users.model.UsersResponse;
import com.travelgo.users.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UsersController {
    private final UserService userService;

    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public UsersResponse getUsers() {
        List<UserDto> all = userService.getAll();
        UsersResponse resp = new UsersResponse();
        resp.setUsers(all);
        resp.setTotal(all.size());
        resp.setSkip(0);
        resp.setLimit(all.size());
        return resp;
    }

    @GetMapping("/users/search")
    public UsersResponse searchUsers(@RequestParam("q") String q) {
        List<UserDto> result = userService.search(q);
        UsersResponse resp = new UsersResponse();
        resp.setUsers(result);
        resp.setTotal(result.size());
        resp.setSkip(0);
        resp.setLimit(result.size());
        return resp;
    }

    @GetMapping("/users/{id}")
    public UserDto getById(@PathVariable("id") Integer id) {
        return userService.getById(id);
    }

    @PostMapping("/users")
    public ResponseEntity<UserDto> create(@RequestBody UserDto user) {
        UserDto created = userService.create(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/users/{id}")
    public UserDto update(@PathVariable("id") Integer id, @RequestBody UserDto user) {
        return userService.update(id, user);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
