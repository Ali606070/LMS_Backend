package com.library.task.controllers;

import com.library.task.dtos.AuthResponseDto;
import com.library.task.dtos.LogInRequestDto;
import com.library.task.dtos.SignUpRequestDto;
import com.library.task.services.impl.AuthenticationService;
import com.library.task.services.impl.JwtService;
import com.library.task.services.impl.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> createAuthenticationToken(@Valid @RequestBody LogInRequestDto authenticationRequest) throws Exception {
        AuthResponseDto logInRespons= authenticationService.loginService(authenticationRequest);
        return ResponseEntity.ok(logInRespons);
    }

    @PostMapping(value ="/signup", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> registerUser(@Valid @ModelAttribute SignUpRequestDto signUpRequest) throws Exception {
        return ResponseEntity.ok(authenticationService.signUpService(signUpRequest));
    }
}