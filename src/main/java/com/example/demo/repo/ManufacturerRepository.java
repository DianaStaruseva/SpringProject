package com.example.demo.repo;

import com.example.demo.models.Category;
import com.example.demo.models.Form;
import com.example.demo.models.Manufacturer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ManufacturerRepository extends CrudRepository<Manufacturer, Long>{
    List<Manufacturer> findByNameContains(String name);
}
