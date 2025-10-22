package com.staffmanagement.HR.controller;

import com.staffmanagement.HR.service.HrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/hr")
public class HrDashboardController {
    @Autowired
    private HrService hrService;

    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        model.addAttribute("staffCount", hrService.getStaffCount());
        model.addAttribute("pendingLeavesCount", hrService.getPendingLeavesCount());
        model.addAttribute("attendanceOverview", hrService.getAttendanceOverview());
        return "hr/hr-dashboard";
    }
}