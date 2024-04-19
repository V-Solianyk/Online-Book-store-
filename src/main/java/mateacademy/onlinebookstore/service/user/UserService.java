package mateacademy.onlinebookstore.service.user;

import mateacademy.onlinebookstore.dto.user.RegisterUserRequestDto;
import mateacademy.onlinebookstore.dto.user.UserResponseDto;
import mateacademy.onlinebookstore.exception.RegistrationException;

public interface UserService {
    UserResponseDto register(RegisterUserRequestDto userRequestDto) throws RegistrationException;
}
