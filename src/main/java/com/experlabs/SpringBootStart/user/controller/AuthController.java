package com.experlabs.SpringBootStart.user.controller;

import com.experlabs.SpringBootStart.core.models.ResponseBody;
import com.experlabs.SpringBootStart.user.models.AuthenticationRequest;
import com.experlabs.SpringBootStart.user.models.AuthenticationResponse;
import com.experlabs.SpringBootStart.user.models.RegisterRequest;
import com.experlabs.SpringBootStart.user.service.AuthServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthServiceImpl service;

    @PostMapping(path = "/register")
    public ResponseEntity<Object> register (
            @RequestBody RegisterRequest request
    ) {
        try {
            return ResponseEntity.ok(service.register(request));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseBody(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
        }
    }

    @PostMapping(path = "/login")
    public ResponseEntity<AuthenticationResponse> authenticate (
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }
}
