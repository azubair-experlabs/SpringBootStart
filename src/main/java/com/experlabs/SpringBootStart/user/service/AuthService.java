package com.experlabs.SpringBootStart.user.service;

import com.experlabs.SpringBootStart.core.models.AuthenticationRequest;
import com.experlabs.SpringBootStart.core.models.AuthenticationResponse;
import com.experlabs.SpringBootStart.core.models.RegisterRequest;

public interface AuthService {
    AuthenticationResponse register(RegisterRequest request);

    AuthenticationResponse authenticate(AuthenticationRequest request);
}
