package com.experlabs.SpringBootStart.user.service;

import com.experlabs.SpringBootStart.core.utils.HelperMethods;
import com.experlabs.SpringBootStart.user.configs.JwtService;
import com.experlabs.SpringBootStart.user.models.AuthenticationResponse;
import com.experlabs.SpringBootStart.user.models.AuthenticationRequest;
import com.experlabs.SpringBootStart.user.models.RegisterRequest;
import com.experlabs.SpringBootStart.user.respository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import com.experlabs.SpringBootStart.user.models.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthenticationResponse register(RegisterRequest request) {
        if (request.getName() == null || request.getName().isBlank())
            throw new IllegalArgumentException("name not valid");
        if (request.getPassword() == null || request.getPassword().isBlank())
            throw new IllegalArgumentException("password not valid");
        if (request.getEmail() == null || request.getEmail().isBlank() || !HelperMethods.isValidEmail(request.getEmail()))
            throw new IllegalArgumentException("email not valid");
        if (request.getDob() == null || !HelperMethods.isValidDOB(request.getDob()))
            throw new IllegalArgumentException("date of birth not valid");

        boolean isTaken = repository.findUserByEmail(request.getEmail()).isPresent();
        if (isTaken)
            throw new IllegalStateException("email already taken!");
        else {
            var user = User.builder()
                    .name(request.getName())
                    .email(request.getEmail())
                    .dob(request.getDob())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .build();
            var savedUser = repository.save(user);
            var jwtToken = jwtService.generateToken(user);
            return AuthenticationResponse.builder()
                    .user(savedUser)
                    .token(jwtToken)
                    .build();
        }
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repository.findUserByEmail(request.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return new AuthenticationResponse(user, jwtToken);
    }
}
