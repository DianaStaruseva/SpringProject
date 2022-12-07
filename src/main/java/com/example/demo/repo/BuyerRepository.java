package com.example.demo.repo;

import com.example.demo.models.Buyer;
import org.springframework.data.repository.CrudRepository;

public interface BuyerRepository  extends CrudRepository<Buyer, Long>{
    Iterable<Buyer> getAllBySurname(String surname);
}
