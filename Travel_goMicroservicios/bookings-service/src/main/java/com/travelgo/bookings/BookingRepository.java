package com.travelgo.bookings;

import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class BookingRepository {

    private final Map<Long, Booking> storage = new ConcurrentHashMap<>();
    private final AtomicLong sequence = new AtomicLong(0);

    public BookingRepository() {
        Booking demo = new Booking(
                sequence.incrementAndGet(),
                1L,
                "Santiago - Atacama",
                LocalDate.now().plusDays(15),
                250000.0,
                "CREATED"
        );
        storage.put(demo.getId(), demo);
    }

    public List<Booking> findAll() {
        return new ArrayList<>(storage.values());
    }

    public Optional<Booking> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    public Booking save(Booking booking) {
        if (booking.getId() == null) {
            booking.setId(sequence.incrementAndGet());
        }
        storage.put(booking.getId(), booking);
        return booking;
    }

    public void deleteById(Long id) {
        storage.remove(id);
    }

    public boolean existsById(Long id) {
        return storage.containsKey(id);
    }
}
