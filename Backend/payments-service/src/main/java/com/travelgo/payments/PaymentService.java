package com.travelgo.payments;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {

    private final PaymentRepository repository;

    public PaymentService(PaymentRepository repository) {
        this.repository = repository;
    }

    public List<Payment> findAll() {
        return repository.findAll();
    }

    public Payment findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found: " + id));
    }

    public Payment create(Payment payment) {
        payment.setId(null);
        return repository.save(payment);
    }

    public Payment update(Long id, Payment payment) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Payment not found: " + id);
        }
        payment.setId(id);
        return repository.save(payment);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
