package com.staffmanagement.Department.service;

import com.staffmanagement.Department.model.Department;
import com.staffmanagement.Department.model.Employee;
import com.staffmanagement.Department.repository.DepartmentRepository;
import com.staffmanagement.Department.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service

public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Department> getAllDepartments() {
        return departmentRepository.findAllWithEmployees();
    }

    public Department getDepartmentById(Long id) {
        return departmentRepository.findByIdWithEmployees(id)
                .orElseThrow(() -> new RuntimeException("Department not found with id: " + id));
    }

    @Transactional
    public Department createDepartment(Department department) {
        // Trim the department name
        String trimmedName = department.getDepartmentName().trim();

        // Check if department with same name already exists (case-insensitive)
        if (departmentRepository.existsByDepartmentNameIgnoreCase(trimmedName)) {
            throw new RuntimeException("Department with name '" + trimmedName + "' already exists");
        }

        department.setDepartmentName(trimmedName);

        // Save to database (description won't be saved as it's @Transient)
        return departmentRepository.save(department);
    }

    @Transactional
    public Department updateDepartment(Long id, Department updatedDepartment) {
        Department existingDepartment = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found with id: " + id));

        String trimmedName = updatedDepartment.getDepartmentName().trim();

        // Check if another department with the same name exists (case-insensitive)
        departmentRepository.findByDepartmentName(trimmedName).ifPresent(dept -> {
            if (!dept.getDepartmentId().equals(id)) {
                throw new RuntimeException("Department with name '" + trimmedName + "' already exists");
            }
        });

        existingDepartment.setDepartmentName(trimmedName);

        // Note: description is @Transient, so it won't be saved to DB
        // but it will be included in the JSON response
        existingDepartment.setDepartmentDescription(updatedDepartment.getDepartmentDescription());

        return departmentRepository.save(existingDepartment);
    }

    @Transactional
    public void deleteDepartment(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found with id: " + id));

        // Check if department has employees
        List<Employee> employees = employeeRepository.findByDepartmentId(id);
        if (!employees.isEmpty()) {
            throw new RuntimeException("Cannot delete department with assigned employees. Please unassign all employees first.");
        }

        departmentRepository.deleteById(id);
    }

    @Transactional
    public void assignEmployeeToDepartment(Long departmentId, Long employeeId) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new RuntimeException("Department not found with id: " + departmentId));

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + employeeId));

        employee.setDepartment(department);
        employeeRepository.save(employee);
    }
}