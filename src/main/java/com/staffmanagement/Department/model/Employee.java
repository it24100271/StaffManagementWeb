package com.staffmanagement.Department.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "Employees")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    @JsonProperty("id")
    private Long employeeId;

    @Column(name = "first_name", nullable = false)
    @JsonProperty("firstName")
    private String firstName;

    @Column(name = "last_name", nullable = false)
    @JsonProperty("lastName")
    private String lastName;

    @Column(nullable = false, unique = true)
    @JsonProperty("email")
    private String email;

    @Column(name = "phone")
    @JsonProperty("phone")
    private String phone;

    @Column(name = "hire_date")
    @JsonProperty("hireDate")
    private LocalDate hireDate;

    @Column(name = "job_role")
    @JsonProperty("role")
    private String jobRole;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    @JsonBackReference
    private Department department;

    @Column(name = "user_id")
    @JsonProperty("userId")
    private Long userId;

    // Computed property for full name
    @Transient
    @JsonProperty("name")
    public String getFullName() {
        return firstName + " " + lastName;
    }
}