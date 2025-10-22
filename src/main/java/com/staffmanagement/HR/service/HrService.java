package com.staffmanagement.HR.service;

import com.staffmanagement.HR.repository.LeaveRequestRepository;
import com.staffmanagement.HR.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HrService {
    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private LeaveRequestRepository leaveRequestRepository;

    public long getStaffCount() {
        return staffRepository.count();
    }

    public long getPendingLeavesCount() {
        return leaveRequestRepository.countByStatus("PENDING");
    }

    public String getAttendanceOverview() {
        long totalStaff = staffRepository.count();
        return "Attendance tracked for " + totalStaff + " staff (details TBD)";
    }
}