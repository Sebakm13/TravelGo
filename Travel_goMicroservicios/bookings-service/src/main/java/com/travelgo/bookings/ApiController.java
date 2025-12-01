package com.travelgo.bookings;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
public class ApiController {

    private final BookingService service;

    public ApiController(BookingService service) {
        this.service = service;
    }

    @GetMapping
    public List<Booking> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Booking findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public ResponseEntity<Booking> create(@RequestBody Booking booking) {
        Booking created = service.create(booking);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public Booking update(@PathVariable Long id, @RequestBody Booking booking) {
        return service.update(id, booking);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/status")
    public String status() {
        return "Bookings service OK";
    }
}
