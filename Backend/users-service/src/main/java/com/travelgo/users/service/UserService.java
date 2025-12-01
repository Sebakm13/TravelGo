package com.travelgo.users.service;

import com.travelgo.users.model.UserDto;
import com.travelgo.users.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public List<UserDto> getAll() {
        return repository.findAll();
    }

    public UserDto getById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found: " + id));
    }

    public UserDto create(UserDto user) {
        user.setId(null);
        return repository.save(user);
    }

    public UserDto update(Integer id, UserDto user) {
        user.setId(id);
        return repository.save(user);
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }

    public List<UserDto> search(String q) {
        return repository.searchByUsername(q);
    }
}
