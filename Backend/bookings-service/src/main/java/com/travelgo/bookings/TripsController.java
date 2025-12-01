package com.travelgo.bookings;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trips")
public class TripsController {

    private final BookingService service;

    public TripsController(BookingService service) {
        this.service = service;
    }

    @GetMapping
    public List<Booking> getTrips() {
        return service.findAll();
    }

    @PostMapping
    public ResponseEntity<Booking> createTrip(@RequestBody Booking booking) {
        Booking created = service.create(booking);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
}
