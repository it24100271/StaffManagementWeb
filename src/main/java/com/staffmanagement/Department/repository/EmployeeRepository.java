package com.staffmanagement.Department.repository;

import com.staffmanagement.Department.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("SELECT e FROM Employee e LEFT JOIN FETCH e.department")
    List<Employee> findAllWithDepartment();

    @Query("SELECT e FROM Employee e LEFT JOIN FETCH e.department WHERE e.department.departmentId = :departmentId")
    List<Employee> findByDepartmentId(@Param("departmentId") Long departmentId);

    List<Employee> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(String firstName, String lastName);

    Optional<Employee> findByEmail(String email);

    @Override
    @Query("SELECT e FROM Employee e LEFT JOIN FETCH e.department")
    List<Employee> findAll();
}