package com.experlabs.SpringBootStart.user.service;

import com.experlabs.SpringBootStart.user.models.AuthenticationRequest;
import com.experlabs.SpringBootStart.user.models.AuthenticationResponse;
import com.experlabs.SpringBootStart.user.models.RegisterRequest;

public interface AuthService {
    AuthenticationResponse register(RegisterRequest request);

    AuthenticationResponse authenticate(AuthenticationRequest request);
}
