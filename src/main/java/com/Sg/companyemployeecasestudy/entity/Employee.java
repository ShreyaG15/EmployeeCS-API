package com.Sg.companyemployeecasestudy.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    @Id
    @GeneratedValue
    @Column(length = 10)
    private Integer employeeId;

    @Column(length = 50)
    private String firstName;

    @Column(length = 50)
    private String lastName;

    @Column(length = 30)
    private LocalDate dob;

    @Column(length = 30)
    private LocalDate joiningDate;

    @Column(length = 3)
    private Integer age;

    @Column(length = 50)
    private String department;

    @Column(length = 50)
    private String createdBy;

    private LocalDateTime createdOn;

    @Column(length = 50)
    private String updatedBy;

    private LocalDateTime updatedOn;

    @ManyToOne
    private Company company;
}
