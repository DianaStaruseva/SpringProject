package com.example.demo.repo;

import com.example.demo.models.Author;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AuthorRepository extends CrudRepository<Author, Long>{
        List<Author> findBySurname(String surname); //Точный поиск
}
