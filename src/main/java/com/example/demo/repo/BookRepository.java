package com.example.demo.repo;

import com.example.demo.models.Book;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BookRepository extends CrudRepository<Book, Long> {


    List<Book> findByName(String name); //Точный поиск
    List<Book> findByNameContains(String name); //Поиск по совпадениям символов

}


