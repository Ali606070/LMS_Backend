package com.library.task.services;

import com.library.task.dtos.AuthResponseDto;
import com.library.task.dtos.LogInRequestDto;
import com.library.task.dtos.SignUpRequestDto;
import com.library.task.dtos.UserDto;


public interface IAuthenticationService {
    AuthResponseDto loginService(LogInRequestDto logInRequest);
    UserDto signUpService(SignUpRequestDto signUpRequest);
}
