package com.travelgo.users.repository;

import com.travelgo.users.model.UserDto;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class UserRepository {
    private final Map<Integer, UserDto> storage = new ConcurrentHashMap<>();
    private final AtomicInteger sequence = new AtomicInteger(0);

    public UserRepository() {
        // usuario demo
        UserDto u = new UserDto();
        u.setId(sequence.incrementAndGet());
        u.setUsername("demo");
        u.setEmail("demo@travelgo.com");
        u.setFirstName("Demo");
        u.setLastName("User");
        u.setImage(null);
        storage.put(u.getId(), u);
    }

    public List<UserDto> findAll() {
        return new ArrayList<>(storage.values());
    }

    public Optional<UserDto> findById(Integer id) {
        return Optional.ofNullable(storage.get(id));
    }

    public UserDto save(UserDto user) {
        if (user.getId() == null) {
            user.setId(sequence.incrementAndGet());
        }
        storage.put(user.getId(), user);
        return user;
    }

    public void deleteById(Integer id) {
        storage.remove(id);
    }

    public List<UserDto> searchByUsername(String q) {
        String lower = q.toLowerCase();
        List<UserDto> result = new ArrayList<>();
        for (UserDto u : storage.values()) {
            if (u.getUsername() != null && u.getUsername().toLowerCase().contains(lower)) {
                result.add(u);
            }
        }
        return result;
    }
}
