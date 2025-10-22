package com.staffmanagement.Department.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "Departments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "department_id")
    @JsonProperty("id")
    private Long departmentId;

    @Column(name = "department_name", nullable = false, unique = true)
    @JsonProperty("name")
    private String departmentName;

    // This field will be stored in memory only (not in DB)
    // but will be included in JSON responses for frontend compatibility
    @Column(name = "department_description")
    @JsonProperty("description")
    private String departmentDescription;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Employee> employees;

    // Getter and Setter for departmentName
    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    // Getter and Setter for departmentDescription
    public String getDepartmentDescription() {
        return departmentDescription;
    }

    public void setDepartmentDescription(String departmentDescription) {
        this.departmentDescription = departmentDescription;
    }

    public long getStaffCount() {
        return employees != null ? employees.size() : 0;
    }
}