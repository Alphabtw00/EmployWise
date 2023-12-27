package com.example.employwise.entities;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Employee {

    @Id
    private String employeeId;
    private String employeeName;
    private String phoneNumber;
    private String email;
    private String reportsTo;
    private String profileImage;

}
