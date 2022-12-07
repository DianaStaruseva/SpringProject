package com.example.demo.repo;

import com.example.demo.models.Employee;
import com.example.demo.models.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Long> {
    //List<Product> findByName_product(String name_product); //Точный поиск
    //List<Product> findByName_productContains(String name_product);
}
