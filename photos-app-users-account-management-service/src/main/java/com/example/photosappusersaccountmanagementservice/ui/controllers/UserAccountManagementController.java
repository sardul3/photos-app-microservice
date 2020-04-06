package com.example.photosappusersaccountmanagementservice.ui.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("accounts")
public class UserAccountManagementController {

    @GetMapping("status")
    public String getStatus() {
        return "working";
    }
}
