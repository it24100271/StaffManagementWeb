package com.staffmanagement.HR.repository;

import com.staffmanagement.HR.entity.LeaveRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {
    List<LeaveRequest> findByStatus(String status);
    long countByStatus(String status); // Added to fix the error
}