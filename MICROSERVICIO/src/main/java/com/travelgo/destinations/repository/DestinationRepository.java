package com.travelgo.destinations.repository;

import com.travelgo.destinations.model.Destination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DestinationRepository extends JpaRepository<Destination, Long> {

    List<Destination> findByCategoryIgnoreCase(String category);

    List<Destination> findByCityContainingIgnoreCaseOrCountryContainingIgnoreCaseOrNameContainingIgnoreCase(
            String city, String country, String name
    );
}
