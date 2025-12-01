package com.travelgo.users;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public List<User> findAll() {
        return repository.findAll();
    }

    public User findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found: " + id));
    }

    public User create(User user) {
        user.setId(null);
        return repository.save(user);
    }

    public User update(Long id, User user) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("User not found: " + id);
        }
        user.setId(id);
        return repository.save(user);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
