package com.example.demo.repo;

import com.example.demo.models.LibraryCard;
import org.springframework.data.repository.CrudRepository;

public interface LibraryCardRepository extends CrudRepository<LibraryCard, Long> {
    LibraryCard findByNumber(String number);
}
