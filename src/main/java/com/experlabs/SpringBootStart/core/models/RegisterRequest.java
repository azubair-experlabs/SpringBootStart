package com.experlabs.SpringBootStart.core.models;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RegisterRequest {
    private String name;
    private String email;
    private String password;
    private LocalDate dob;
}
