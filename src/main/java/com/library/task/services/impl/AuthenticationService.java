package com.library.task.services.impl;


import com.library.task.dtos.*;
import com.library.task.exceptions.ConflictException;
import com.library.task.exceptions.ResourceNotFoundException;
import com.library.task.models.Book;
import com.library.task.models.Role;
import com.library.task.models.User;
import com.library.task.repositories.UserRepository;
import com.library.task.services.IAuthenticationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AuthenticationService implements IAuthenticationService {

    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    UserService userService;

    @Autowired
    private ModelMapper modelMapper;



    public AuthResponseDto loginService(LogInRequestDto logInRequestDto)  {
        final String jwt = jwtService.createJwtToken(logInRequestDto);
        AuthResponseDto authenticationResponse = new AuthResponseDto(jwt);
        return authenticationResponse;
    }

    public UserDto signUpService(SignUpRequestDto signUpRequestDto) {
        if (userService.findByEmail(signUpRequestDto.getEmail()).isPresent()) {
            throw new ConflictException("User already exists with email: " + signUpRequestDto.getEmail());
        }
        User user = modelMapper.map(signUpRequestDto, User.class);
        Role role = userService.findRoleByName(signUpRequestDto.getRole())
                .orElseThrow(() -> new ResourceNotFoundException("Role not found: " + signUpRequestDto.getRole()));

        user.setRole(role);
        User savedUser = userService.save(user);
        return modelMapper.map(savedUser, UserDto.class);
    }
}

