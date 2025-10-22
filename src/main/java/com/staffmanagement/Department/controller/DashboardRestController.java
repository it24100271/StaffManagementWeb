package com.staffmanagement.Department.controller;

import com.staffmanagement.Department.model.Department;
import com.staffmanagement.Department.model.Employee;
import com.staffmanagement.Department.service.DepartmentService;
import com.staffmanagement.Department.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class DashboardRestController {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/departments")
    public ResponseEntity<List<Department>> getAllDepartments() {
        return ResponseEntity.ok(departmentService.getAllDepartments());
    }

    @PostMapping("/departments")
    public ResponseEntity<?> createDepartment(@RequestBody Map<String, Object> request) {
        try {
            // Get name from request (supports both frontend key variations)
            String name = extractFirstNonBlank(request, "name", "departmentName");

            // Validate name
            if (name == null) {
                Map<String, String> error = new HashMap<>();
                error.put("error", "Department name is required");
                return ResponseEntity.badRequest().body(error);
            }

            Department department = new Department();
            department.setDepartmentName(name);

            // Get description from request (optional)
            String description = extractFirstNonBlank(request, "description", "departmentDescription");
            if (description != null) {
                // Validate description length (maximum 20 words)
                String[] words = description.split("\\s+");
                if (words.length > 20) {
                    Map<String, String> error = new HashMap<>();
                    error.put("error", "Description cannot exceed 20 words");
                    return ResponseEntity.badRequest().body(error);
                }
                department.setDepartmentDescription(description);
            } else {
                // Set empty description if not provided
                department.setDepartmentDescription("");
            }

            Department createdDepartment = departmentService.createDepartment(department);
            return ResponseEntity.ok(createdDepartment);

        } catch (RuntimeException e) {
            // Return the actual error message from the service
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);

        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "An unexpected error occurred: " + e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @PutMapping("/departments/{id}")
    public ResponseEntity<?> updateDepartment(@PathVariable Long id, @RequestBody Map<String, Object> request) {
        try {
            Department department = new Department();

            // Get name from request
            String name = extractFirstNonBlank(request, "name", "departmentName");
            if (name == null) {
                Map<String, String> error = new HashMap<>();
                error.put("error", "Department name is required");
                return ResponseEntity.badRequest().body(error);
            }

            department.setDepartmentName(name);

            // Get description from request (optional)
            String description = extractFirstNonBlank(request, "description", "departmentDescription");
            if (description != null) {
                // Validate description length (maximum 20 words)
                String[] words = description.split("\\s+");
                if (words.length > 20) {
                    Map<String, String> error = new HashMap<>();
                    error.put("error", "Description cannot exceed 20 words");
                    return ResponseEntity.badRequest().body(error);
                }
                department.setDepartmentDescription(description);
            } else {
                // Set empty description if not provided
                department.setDepartmentDescription("");
            }

            Department updatedDepartment = departmentService.updateDepartment(id, department);
            return ResponseEntity.ok(updatedDepartment);

        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @DeleteMapping("/departments/{id}")
    public ResponseEntity<?> deleteDepartment(@PathVariable Long id) {
        try {
            departmentService.deleteDepartment(id);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Department deleted successfully");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    // Employee/Staff endpoints
    @GetMapping("/staff")
    public ResponseEntity<List<Employee>> getAllStaff() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @PostMapping("/staff")
    public ResponseEntity<?> createStaff(@RequestBody Map<String, Object> request) {
        try {
            Employee employee = new Employee();

            // Handle name field - split into first and last name
            String fullName = (String) request.get("name");
            if (fullName == null || fullName.trim().isEmpty()) {
                Map<String, String> error = new HashMap<>();
                error.put("error", "Employee name is required");
                return ResponseEntity.badRequest().body(error);
            }

            String[] nameParts = fullName.trim().split("\\s+", 2);
            employee.setFirstName(nameParts[0]);
            employee.setLastName(nameParts.length > 1 ? nameParts[1] : "");

            // Validate and set email
            String email = (String) request.get("email");
            if (email == null || email.trim().isEmpty()) {
                Map<String, String> error = new HashMap<>();
                error.put("error", "Email is required");
                return ResponseEntity.badRequest().body(error);
            }
            employee.setEmail(email);

            employee.setPhone((String) request.get("phone"));
            employee.setJobRole((String) request.get("role"));

            Employee createdEmployee = employeeService.createEmployee(employee);
            return ResponseEntity.ok(createdEmployee);

        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @PutMapping("/staff/{id}")
    public ResponseEntity<?> updateStaff(@PathVariable Long id, @RequestBody Map<String, Object> request) {
        try {
            Employee employee = new Employee();

            // Handle name field
            String fullName = (String) request.get("name");
            if (fullName == null || fullName.trim().isEmpty()) {
                Map<String, String> error = new HashMap<>();
                error.put("error", "Employee name is required");
                return ResponseEntity.badRequest().body(error);
            }

            String[] nameParts = fullName.trim().split("\\s+", 2);
            employee.setFirstName(nameParts[0]);
            employee.setLastName(nameParts.length > 1 ? nameParts[1] : "");

            // Validate and set email
            String email = (String) request.get("email");
            if (email == null || email.trim().isEmpty()) {
                Map<String, String> error = new HashMap<>();
                error.put("error", "Email is required");
                return ResponseEntity.badRequest().body(error);
            }
            employee.setEmail(email);

            employee.setPhone((String) request.get("phone"));
            employee.setJobRole((String) request.get("role"));

            Employee updatedEmployee = employeeService.updateEmployee(id, employee);
            return ResponseEntity.ok(updatedEmployee);

        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @DeleteMapping("/staff/{id}")
    public ResponseEntity<?> deleteStaff(@PathVariable Long id) {
        try {
            employeeService.deleteEmployee(id);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Employee deleted successfully");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @PostMapping("/staff/{staffId}/assign/{departmentId}")
    public ResponseEntity<?> assignStaffToDepartment(@PathVariable Long staffId, @PathVariable Long departmentId) {
        try {
            Employee assignedEmployee = employeeService.assignEmployeeToDepartment(staffId, departmentId);
            return ResponseEntity.ok(assignedEmployee);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @PostMapping("/staff/{staffId}/unassign")
    public ResponseEntity<?> unassignStaff(@PathVariable Long staffId) {
        try {
            Employee unassignedEmployee = employeeService.unassignEmployeeFromDepartment(staffId);
            return ResponseEntity.ok(unassignedEmployee);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    private String extractFirstNonBlank(Map<String, Object> request, String... keys) {
        for (String key : keys) {
            Object value = request.get(key);
            if (value instanceof String str) {
                String trimmed = str.trim();
                if (!trimmed.isEmpty()) {
                    return trimmed;
                }
            }
        }
        return null;
    }
}