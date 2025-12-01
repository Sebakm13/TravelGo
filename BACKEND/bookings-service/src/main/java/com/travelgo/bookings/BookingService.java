package com.travelgo.bookings;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {

    private final BookingRepository repository;

    public BookingService(BookingRepository repository) {
        this.repository = repository;
    }

    public List<Booking> findAll() {
        return repository.findAll();
    }

    public Booking findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found: " + id));
    }

    public Booking create(Booking booking) {
        booking.setId(null);
        if (booking.getStatus() == null) {
            booking.setStatus("CREATED");
        }
        return repository.save(booking);
    }

    public Booking update(Long id, Booking booking) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Booking not found: " + id);
        }
        booking.setId(id);
        return repository.save(booking);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
