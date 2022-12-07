package com.example.demo.repo;

import com.example.demo.models.Category;
import com.example.demo.models.Employee;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CategoryRepository  extends CrudRepository<Category, Long> {
    List<Category> findByNameContains(String name);
}
