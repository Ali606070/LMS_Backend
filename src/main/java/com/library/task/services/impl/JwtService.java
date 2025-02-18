package com.library.task.services.impl;


import com.library.task.dtos.LogInRequestDto;
import com.library.task.models.User;
import com.library.task.security.CustomUserDetails;
import com.library.task.services.IJwtService;
import com.library.task.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtService implements IJwtService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    public String createJwtToken(LogInRequestDto authenticationRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authenticationRequest.getEmail(), authenticationRequest.getPassword()));

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());
        Long userId = ((CustomUserDetails) userDetails).getId();
        String role = userDetails.getAuthorities().iterator().next().getAuthority();
        return jwtUtil.generateToken(userId, role);
    }
}

