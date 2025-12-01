package com.travelgo.users.service;

import com.travelgo.users.model.*;
import com.travelgo.users.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    private UserDto toDto(UserEntity entity) {
        if (entity == null) return null;
        UserDto dto = new UserDto();
        dto.setId(entity.getId());
        dto.setUsername(entity.getUsername());
        dto.setEmail(entity.getEmail());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setImage(entity.getImage());
        return dto;
    }

    private UserEntity toEntity(UserDto dto) {
        if (dto == null) return null;
        UserEntity entity = new UserEntity();
        entity.setId(dto.getId());
        entity.setUsername(dto.getUsername());
        entity.setEmail(dto.getEmail());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setImage(dto.getImage());
        return entity;
    }

    public UsersResponse getAll(String query, Integer skip, Integer limit) {
        List<UserEntity> all = repository.findAll();

        List<UserEntity> filtered = all;
        if (query != null && !query.isBlank()) {
            String q = query.toLowerCase();
            filtered = all.stream()
                    .filter(u ->
                            (u.getUsername() != null && u.getUsername().toLowerCase().contains(q)) ||
                            (u.getEmail() != null && u.getEmail().toLowerCase().contains(q)) ||
                            (u.getFirstName() != null && u.getFirstName().toLowerCase().contains(q)) ||
                            (u.getLastName() != null && u.getLastName().toLowerCase().contains(q))
                    )
                    .collect(Collectors.toList());
        }

        int s = skip != null ? skip : 0;
        int l = limit != null && limit > 0 ? limit : filtered.size();
        int fromIndex = Math.min(s, filtered.size());
        int toIndex = Math.min(fromIndex + l, filtered.size());

        List<UserDto> dtos = filtered.subList(fromIndex, toIndex)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());

        UsersResponse resp = new UsersResponse();
        resp.setUsers(dtos);
        resp.setTotal(filtered.size());
        resp.setSkip(s);
        resp.setLimit(l);
        return resp;
    }

    public UserDto getById(Integer id) {
        return repository.findById(id)
                .map(this::toDto)
                .orElse(null);
    }

    public UserDto create(UserDto dto) {
        UserEntity entity = toEntity(dto);
        entity.setId(null);
        UserEntity saved = repository.save(entity);
        return toDto(saved);
    }

    public UserDto update(Integer id, UserDto dto) {
        Optional<UserEntity> existingOpt = repository.findById(id);
        if (existingOpt.isEmpty()) {
            return null;
        }
        UserEntity existing = existingOpt.get();
        existing.setUsername(dto.getUsername());
        existing.setEmail(dto.getEmail());
        existing.setFirstName(dto.getFirstName());
        existing.setLastName(dto.getLastName());
        existing.setImage(dto.getImage());
        UserEntity saved = repository.save(existing);
        return toDto(saved);
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }

    public UserDto getFirstUser() {
        return repository.findAll().stream().findFirst().map(this::toDto).orElse(null);
    }

    public LoginResponse login(LoginRequest request) {
        String username = request.getUsername();
        if (username == null || username.isBlank()) {
            username = "demo";
        }

        // Try to find existing user
        UserEntity entity = repository.findByUsernameIgnoreCase(username)
                .orElseGet(() -> {
                    UserEntity u = new UserEntity();
                    u.setUsername(username);
                    u.setEmail(username + "@example.com");
                    u.setFirstName("Demo");
                    u.setLastName("User");
                    u.setImage(null);
                    return repository.save(u);
                });

        LoginResponse resp = new LoginResponse();
        resp.setId(entity.getId());
        resp.setUsername(entity.getUsername());
        resp.setEmail(entity.getEmail());
        resp.setFirstName(entity.getFirstName());
        resp.setLastName(entity.getLastName());
        resp.setImage(entity.getImage());
        resp.setToken("fake-token-123");
        Integer expires = request.getExpiresInMins() != null ? request.getExpiresInMins() : 60;
        resp.setExpiresInMins(expires);
        return resp;
    }
}
