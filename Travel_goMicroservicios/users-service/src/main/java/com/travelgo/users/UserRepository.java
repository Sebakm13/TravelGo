package com.travelgo.users;

import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class UserRepository {

    private final Map<Long, User> storage = new ConcurrentHashMap<>();
    private final AtomicLong sequence = new AtomicLong(0);

    public UserRepository() {
        // registro inicial de ejemplo
        User u = new User(sequence.incrementAndGet(), "Usuario Demo", "demo@travelgo.com", "CLIENT");
        storage.put(u.getId(), u);
    }

    public List<User> findAll() {
        return new ArrayList<>(storage.values());
    }

    public Optional<User> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    public User save(User user) {
        if (user.getId() == null) {
            user.setId(sequence.incrementAndGet());
        }
        storage.put(user.getId(), user);
        return user;
    }

    public void deleteById(Long id) {
        storage.remove(id);
    }

    public boolean existsById(Long id) {
        return storage.containsKey(id);
    }
}
