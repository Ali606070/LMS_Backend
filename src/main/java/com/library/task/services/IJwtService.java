package com.library.task.services;

import com.library.task.dtos.LogInRequestDto;

public interface IJwtService {
    String createJwtToken(LogInRequestDto authenticationRequest);
}
