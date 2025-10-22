package com.staffmanagement.Department.controller;

import com.staffmanagement.Department.service.DepartmentService;
import com.staffmanagement.Department.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    private final DepartmentService departmentService;
    private final EmployeeService employeeService;

    @Autowired
    public DashboardController(DepartmentService departmentService, EmployeeService employeeService) {
        this.departmentService = departmentService;
        this.employeeService = employeeService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("departments", departmentService.getAllDepartments());
        model.addAttribute("staff", employeeService.getAllEmployees());
        return "dashboard";
    }
}