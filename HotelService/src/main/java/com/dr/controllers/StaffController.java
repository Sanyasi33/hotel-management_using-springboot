package com.dr.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/staff")
public class StaffController {

    @GetMapping
    public ResponseEntity<List<String>> getAllStaffs(){
        // Simulating the fetch of all staff data
        List<String> staffs = List.of("John Doe", "Jane Smith", "Alice Johnson");
        return ResponseEntity.ok(staffs);
    }
}
