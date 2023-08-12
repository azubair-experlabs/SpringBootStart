package com.experlabs.SpringBootStart.user.models;

import lombok.Data;

@Data
public class AuthenticationRequest {
    private String email;
    private String password;
}
