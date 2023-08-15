package com.experlabs.SpringBootStart.core.models;

import lombok.Data;

@Data
public class AuthenticationRequest {
    private String email;
    private String password;
}
