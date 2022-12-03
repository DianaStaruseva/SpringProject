package com.example.demo.repo;

import com.example.demo.models.Genre;
import com.example.demo.models.Reader;
import org.springframework.data.repository.CrudRepository;

public interface ReaderRepository extends CrudRepository<Reader, Long> {
    Iterable<Reader> getAllByName(String name);
    Reader getByLogin(String login);
}
