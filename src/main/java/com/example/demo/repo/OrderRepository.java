package com.example.demo.repo;

import com.example.demo.models.Manufacturer;
import com.example.demo.models.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long> {
}
