package com.staffmanagement.Department.controller;

import com.staffmanagement.Department.model.Department;
import com.staffmanagement.Department.repository.DepartmentRepository;
import com.staffmanagement.Department.repository.EmployeeRepository;
import com.staffmanagement.Department.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PageController {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    /**
     * Login page - IMPORTANT: Make sure login.html exists at:
     * src/main/resources/templates/login.html
     */
    @GetMapping("/login")
    public String login() {
        System.out.println("DEBUG: Login page requested");
        return "login";
    }

    /** Create Department - for Thymeleaf form pages */
    @PostMapping("/departments/create")
    public String createDepartment(@RequestParam String name,
                                   @RequestParam(required = false) String description,
                                   RedirectAttributes redirectAttributes) {
        try {
            if (departmentRepository.existsByDepartmentNameIgnoreCase(name.trim())) {
                redirectAttributes.addFlashAttribute("error",
                        "Department '" + name + "' already exists. Please choose a different name.");
            } else {
                Department dept = new Department();
                dept.setDepartmentName(name.trim());
                // Note: departmentDescription is @Transient, so this won't be saved to DB
                dept.setDepartmentDescription(description == null ? null : description.trim());
                departmentRepository.save(dept);
                redirectAttributes.addFlashAttribute("success", "Department created successfully.");
            }
        } catch (DataIntegrityViolationException ex) {
            redirectAttributes.addFlashAttribute("error",
                    "Could not save department. A department with this name already exists.");
        }
        return "redirect:/dashboard";
    }

    /** Assign employee to department */
    @PostMapping("/departments/assign")
    public String assignStaff(@RequestParam Long departmentId,
                              @RequestParam Long staffId,
                              RedirectAttributes redirectAttributes) {
        try {
            if (!employeeRepository.existsById(staffId)) {
                redirectAttributes.addFlashAttribute("error",
                        "Employee with ID " + staffId + " does not exist.");
                return "redirect:/dashboard";
            }

            if (!departmentRepository.existsById(departmentId)) {
                redirectAttributes.addFlashAttribute("error",
                        "Department with ID " + departmentId + " does not exist.");
                return "redirect:/dashboard";
            }

            departmentService.assignEmployeeToDepartment(departmentId, staffId);
            redirectAttributes.addFlashAttribute("success",
                    "Employee assigned to department successfully!");

        } catch (RuntimeException ex) {
            if (ex.getMessage().contains("Employee not found")) {
                redirectAttributes.addFlashAttribute("error",
                        "Employee not found. Please verify the employee exists.");
            } else if (ex.getMessage().contains("Department not found")) {
                redirectAttributes.addFlashAttribute("error",
                        "Department not found. Please verify the department exists.");
            } else {
                redirectAttributes.addFlashAttribute("error",
                        "Error assigning employee: " + ex.getMessage());
            }
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("error",
                    "An unexpected error occurred while assigning employee.");
        }

        return "redirect:/dashboard";
    }

    /** List staff/employees page - Thymeleaf template */
    @GetMapping("/staff/list")
    public String showStaff(Model model) {
        model.addAttribute("staffList", employeeRepository.findAll());
        return "staff-list";
    }

    /** List departments page - Thymeleaf template */
    @GetMapping("/departments/list")
    public String showDepartments(Model model) {
        model.addAttribute("departments", departmentRepository.findAll());
        return "department-list";
    }

    /** Layout page - Thymeleaf template */
    @GetMapping("/layout")
    public String layout(Model model) {
        model.addAttribute("departments", departmentRepository.findAll());
        return "layout";
    }
}