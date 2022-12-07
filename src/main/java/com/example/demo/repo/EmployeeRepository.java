package com.example.demo.repo;

import com.example.demo.models.Employee;
import com.example.demo.models.Post;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EmployeeRepository extends CrudRepository<Employee, Long> {

    //List<Employee> findBySurname(String surname);
    List<Employee> findBySurnameContains(String surname);

}

