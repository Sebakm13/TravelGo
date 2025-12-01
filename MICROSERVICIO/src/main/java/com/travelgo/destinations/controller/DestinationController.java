package com.travelgo.destinations.controller;

import com.travelgo.destinations.model.Destination;
import com.travelgo.destinations.service.DestinationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/destinations")
@CrossOrigin(origins = "*")
public class DestinationController {

    private final DestinationService service;

    public DestinationController(DestinationService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Destination>> getAll(
            @RequestParam(required = false) String category,
            @RequestParam(required = false, name = "q") String search
    ) {
        List<Destination> destinations = service.getAll(category, search);
        return ResponseEntity.ok(destinations);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Destination> getById(@PathVariable Long id) {
        Destination destination = service.getById(id);
        return ResponseEntity.ok(destination);
    }

    @PostMapping
    public ResponseEntity<Destination> create(@Valid @RequestBody Destination destination) {
        Destination created = service.create(destination);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Destination> update(
            @PathVariable Long id,
            @Valid @RequestBody Destination destination
    ) {
        Destination updated = service.update(id, destination);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
