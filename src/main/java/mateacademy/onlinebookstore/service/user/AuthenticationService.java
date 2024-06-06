package mateacademy.onlinebookstore.service.user;

import mateacademy.onlinebookstore.dto.user.UserLoginRequestDto;
import mateacademy.onlinebookstore.dto.user.UserLoginResponseDto;

public interface AuthenticationService {
    UserLoginResponseDto login(UserLoginRequestDto requestDto);
}
