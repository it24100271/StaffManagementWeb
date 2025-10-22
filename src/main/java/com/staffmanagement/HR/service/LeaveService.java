package com.staffmanagement.HR.service;

import com.staffmanagement.HR.entity.LeaveRequest;
import com.staffmanagement.HR.repository.LeaveRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LeaveService {
    @Autowired
    private LeaveRequestRepository leaveRequestRepository;

    public List<LeaveRequest> getPendingLeaves() {
        return leaveRequestRepository.findByStatus("PENDING");
    }

    public Optional<LeaveRequest> getLeaveById(Long id) {
        return leaveRequestRepository.findById(id);
    }

    public LeaveRequest approveLeave(Long id) {
        Optional<LeaveRequest> leave = getLeaveById(id);
        if (leave.isPresent()) {
            leave.get().setStatus("APPROVED");
            return leaveRequestRepository.save(leave.get());
        }
        return null;
    }

    public LeaveRequest rejectLeave(Long id) {
        Optional<LeaveRequest> leave = getLeaveById(id);
        if (leave.isPresent()) {
            leave.get().setStatus("REJECTED");
            return leaveRequestRepository.save(leave.get());
        }
        return null;
    }
}
