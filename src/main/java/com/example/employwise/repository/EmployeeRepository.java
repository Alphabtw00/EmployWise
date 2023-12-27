package com.example.employwise.repository;

import com.example.employwise.entities.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface EmployeeRepository extends MongoRepository<Employee, String> {
    Page<Employee> findAll(Pageable pageable);
}
