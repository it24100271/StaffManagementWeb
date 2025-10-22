package com.staffmanagement.Department.service;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DepartmentStats {
    private long totalDepartments;
    private long totalStaff;
    private long departmentsWithStaff;
}