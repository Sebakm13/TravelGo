package com.travelgo.payments;

import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class PaymentRepository {

    private final Map<Long, Payment> storage = new ConcurrentHashMap<>();
    private final AtomicLong sequence = new AtomicLong(0);

    public PaymentRepository() {
        Payment demo = new Payment(
                sequence.incrementAndGet(),
                1L,
                50000.0,
                "APPROVED",
                LocalDateTime.now()
        );
        storage.put(demo.getId(), demo);
    }

    public List<Payment> findAll() {
        return new ArrayList<>(storage.values());
    }

    public Optional<Payment> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    public Payment save(Payment payment) {
        if (payment.getId() == null) {
            payment.setId(sequence.incrementAndGet());
        }
        if (payment.getCreatedAt() == null) {
            payment.setCreatedAt(LocalDateTime.now());
        }
        storage.put(payment.getId(), payment);
        return payment;
    }

    public void deleteById(Long id) {
        storage.remove(id);
    }

    public boolean existsById(Long id) {
        return storage.containsKey(id);
    }
}
