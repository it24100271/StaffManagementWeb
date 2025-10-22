package com.staffmanagement.HR.repository;

import com.staffmanagement.HR.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StaffRepository extends JpaRepository<Staff, Long> {
    List<com.staffmanagement.Department.model.Employee> findByName(String name);
}