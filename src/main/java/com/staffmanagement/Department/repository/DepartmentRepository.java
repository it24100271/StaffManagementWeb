package com.staffmanagement.Department.repository;

import com.staffmanagement.Department.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    boolean existsByDepartmentNameIgnoreCase(String departmentName);

    Optional<Department> findByDepartmentName(String departmentName);

    @Query("SELECT d FROM Department d LEFT JOIN FETCH d.employees WHERE d.departmentId = :id")
    Optional<Department> findByIdWithEmployees(@Param("id") Long id);

    @Query("SELECT d FROM Department d LEFT JOIN FETCH d.employees")
    List<Department> findAllWithEmployees();

    List<Department> findByDepartmentNameContainingIgnoreCase(String departmentName);

    @Query("SELECT COUNT(e) FROM Employee e WHERE e.department.departmentId = :departmentId")
    long countEmployeesByDepartmentId(@Param("departmentId") Long departmentId);

    boolean existsById(Long id);
}