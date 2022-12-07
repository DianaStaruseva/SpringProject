package com.example.demo.repo;

import com.example.demo.models.Place;
import org.springframework.data.repository.CrudRepository;

public interface PlaceRepository extends CrudRepository<Place, Long> {
    Iterable<Place> getAllByAddress(String address);
}
