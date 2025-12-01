package com.travelgo.destinations.service;

import com.travelgo.destinations.model.Destination;
import com.travelgo.destinations.repository.DestinationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DestinationService {

    private final DestinationRepository repository;

    public DestinationService(DestinationRepository repository) {
        this.repository = repository;
    }

    public List<Destination> getAll(String category, String search) {
        List<Destination> base = repository.findAll();

        if (category != null && !category.isBlank() && !"ALL".equalsIgnoreCase(category)) {
            base = repository.findByCategoryIgnoreCase(category);
        }

        if (search != null && !search.isBlank()) {
            base = repository.findByCityContainingIgnoreCaseOrCountryContainingIgnoreCaseOrNameContainingIgnoreCase(
                    search, search, search
            );
        }

        return base;
    }

    public Destination getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Destino no encontrado"));
    }

    public Destination create(Destination destination) {
        destination.setId(null);
        return repository.save(destination);
    }

    public Destination update(Long id, Destination destination) {
        Destination existing = getById(id);
        existing.setName(destination.getName());
        existing.setCity(destination.getCity());
        existing.setCountry(destination.getCountry());
        existing.setCategory(destination.getCategory());
        existing.setPricePerNight(destination.getPricePerNight());
        existing.setImageUrl(destination.getImageUrl());
        existing.setWeatherSummary(destination.getWeatherSummary());
        return repository.save(existing);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
