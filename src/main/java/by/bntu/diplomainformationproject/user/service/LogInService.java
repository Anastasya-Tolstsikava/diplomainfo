package by.bntu.diplomainformationproject.user.service;

import by.bntu.diplomainformationproject.user.dto.AuthToken;
import by.bntu.diplomainformationproject.user.dto.LogInDto;

public interface LogInService {
    public AuthToken logIn(LogInDto logInDto);
}
