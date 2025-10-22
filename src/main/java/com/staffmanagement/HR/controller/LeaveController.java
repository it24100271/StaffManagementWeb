package com.staffmanagement.HR.controller;

import com.staffmanagement.HR.entity.LeaveRequest;
import com.staffmanagement.HR.service.LeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/hr/leave")
public class LeaveController {
    @Autowired
    private LeaveService leaveService;

    @GetMapping("/approvals")
    public String showApprovals(Model model) {
        List<LeaveRequest> pendingLeaves = leaveService.getPendingLeaves();
        model.addAttribute("pendingLeaves", pendingLeaves);
        return "hr/leave-approvals";
    }

    @PostMapping("/approve/{id}")
    public String approveLeave(@PathVariable Long id, Model model) {
        leaveService.approveLeave(id);
        return "redirect:/hr/leave/approvals";
    }

    @PostMapping("/reject/{id}")
    public String rejectLeave(@PathVariable Long id, Model model) {
        leaveService.rejectLeave(id);
        return "redirect:/hr/leave/approvals";
    }
}