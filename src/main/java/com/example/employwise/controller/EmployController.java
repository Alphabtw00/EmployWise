package com.example.employwise.controller;

import com.example.employwise.entities.Employee;
import com.example.employwise.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/employ")
public class EmployController {
    private EmployeeRepository employeeRepository;

    @Autowired
    public EmployController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @PostMapping()
    public ResponseEntity<String> addEmploy(@RequestBody Employee employ){
        if(employ!=null){
            String id = UUID.randomUUID().toString();
            employ.setEmployeeId(id);
            employeeRepository.save(employ);
            return ResponseEntity.ok(id);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid employee data");
    }

    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployee(@RequestParam(defaultValue = "0") int pageNumber,
                                                         @RequestParam(defaultValue = "10") int pageSize,
                                                         @RequestParam(defaultValue = "name") String sort){
        int pageIndex = pageNumber-1;
        Pageable page;
        if(sort.equals("name")){
            page = PageRequest.of(pageIndex, pageSize, Sort.by("employeeName"));
        }
        else if(sort.equals("email")){
            page = PageRequest.of(pageIndex, pageSize, Sort.by(sort));
        }
        else {
            return ResponseEntity.badRequest().build();
        }

        Page<Employee> employeePage = employeeRepository.findAll(page);

        if (employeePage.hasContent()) {
            List<Employee> employees = employeePage.getContent();
            return ResponseEntity.ok(employees);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{employeeId}")
    public ResponseEntity<String> deleteEmployee(@PathVariable String employeeId){
        if(employeeRepository.existsById(employeeId)){
            employeeRepository.deleteById(employeeId);
            return ResponseEntity.ok("Employee with Id: " + employeeId+ " deleted.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee with Id: " + employeeId + " not found.");
    }

    @PutMapping("/{employeeId}")
    public ResponseEntity<String> updateEmployee(@PathVariable String employeeId,
                                                 @RequestBody Employee employee){
        Optional<Employee> employeeOptional = employeeRepository.findById(employeeId);
        if(employeeOptional.isPresent()){
            Employee employeeNew = employeeOptional.get();

            employeeNew.setEmployeeName(employee.getEmployeeName());
            employeeNew.setPhoneNumber(employee.getPhoneNumber());
            employeeNew.setEmail(employee.getEmail());
            employeeNew.setReportsTo(employee.getReportsTo());
            employeeNew.setProfileImage(employee.getProfileImage());

            employeeRepository.save(employeeNew);
            return ResponseEntity.ok("Employee with ID " + employeeId + " updated successfully");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee with ID " + employeeId + " not found");
    }

    @GetMapping("/{employeeId}/manager/{level}")
    public ResponseEntity<Employee> getNthLevelManager(@PathVariable String employeeId,
                                                       @PathVariable int level) {
        if (level <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        Optional<Employee> employeeOptional = employeeRepository.findById(employeeId);

        if (employeeOptional.isPresent()) {
            Employee employee = employeeOptional.get();
            String managerId = employee.getReportsTo();

            for (int i = 1; i <= level; i++) {
                if (managerId == null) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
                }

                Optional<Employee> managerOptional = employeeRepository.findById(managerId);

                if (managerOptional.isPresent()) {
                    employee = managerOptional.get();
                    managerId = employee.getReportsTo();
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
                }
            }

            return ResponseEntity.ok(employee);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}
