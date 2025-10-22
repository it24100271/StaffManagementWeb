package com.staffmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = "com.staffmanagement")
public class StaffManagementWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(StaffManagementWebApplication.class, args);
    }

}
