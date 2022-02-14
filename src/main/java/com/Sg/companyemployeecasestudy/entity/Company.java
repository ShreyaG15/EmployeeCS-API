package com.Sg.companyemployeecasestudy.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Company {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", nullable = false, updatable = false, insertable = false,length = 36)
    private String id;

    @Column(length = 30)
    private String companyName;

    @Column(length = 10)
    private String companyShortCode;

    @Column(length = 50)
    private String address;

    @Column(length = 10)
    private String contactNumber;

    @Column(length = 50)
    private String createdBy;

    private LocalDateTime createdOn;

    @Column(length = 50)
    private String updatedBy;

    private LocalDateTime updatedOn;

    @OneToMany(mappedBy = "company")
    private List<Employee> employee;
}
