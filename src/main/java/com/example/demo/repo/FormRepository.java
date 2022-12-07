package com.example.demo.repo;

import com.example.demo.models.Form;
import org.springframework.data.repository.CrudRepository;

public interface FormRepository extends CrudRepository<Form, Long>{
    Iterable<Form> getAllByName(String name);
}
