package com.staffmanagement.Department.service;

import com.staffmanagement.Department.model.Department;
import com.staffmanagement.Department.model.Employee;
import com.staffmanagement.Department.repository.DepartmentRepository;
import com.staffmanagement.Department.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAllWithDepartment();
    }

    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
    }

    @Transactional
    public Employee createEmployee(Employee employee) {
        // Check if email already exists
        if (employee.getEmail() != null && !employee.getEmail().trim().isEmpty()) {
            employeeRepository.findByEmail(employee.getEmail().trim()).ifPresent(e -> {
                throw new RuntimeException("Employee with email '" + employee.getEmail() + "' already exists");
            });
        }

        // Set hire date to today if not provided
        if (employee.getHireDate() == null) {
            employee.setHireDate(LocalDate.now());
        }

        return employeeRepository.save(employee);
    }

    @Transactional
    public Employee updateEmployee(Long id, Employee updatedEmployee) {
        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));

        // Check if email is being changed to an existing one
        if (updatedEmployee.getEmail() != null && !updatedEmployee.getEmail().trim().isEmpty()) {
            String newEmail = updatedEmployee.getEmail().trim();
            employeeRepository.findByEmail(newEmail).ifPresent(e -> {
                if (!e.getEmployeeId().equals(id)) {
                    throw new RuntimeException("Employee with email '" + newEmail + "' already exists");
                }
            });
            existingEmployee.setEmail(newEmail);
        }

        existingEmployee.setFirstName(updatedEmployee.getFirstName());
        existingEmployee.setLastName(updatedEmployee.getLastName());
        existingEmployee.setPhone(updatedEmployee.getPhone());
        existingEmployee.setJobRole(updatedEmployee.getJobRole());

        return employeeRepository.save(existingEmployee);
    }

    @Transactional
    public void deleteEmployee(Long id) {
        if (!employeeRepository.existsById(id)) {
            throw new RuntimeException("Employee not found with id: " + id);
        }
        employeeRepository.deleteById(id);
    }

    @Transactional
    public Employee assignEmployeeToDepartment(Long employeeId, Long departmentId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + employeeId));

        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new RuntimeException("Department not found with id: " + departmentId));

        employee.setDepartment(department);
        return employeeRepository.save(employee);
    }

    @Transactional
    public Employee unassignEmployeeFromDepartment(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + employeeId));

        employee.setDepartment(null);
        return employeeRepository.save(employee);
    }

    public List<Employee> getEmployeesByDepartment(Long departmentId) {
        return employeeRepository.findByDepartmentId(departmentId);
    }
}