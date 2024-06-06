package mateacademy.onlinebookstore.service.impl;

import lombok.RequiredArgsConstructor;
import mateacademy.onlinebookstore.dto.user.UserLoginRequestDto;
import mateacademy.onlinebookstore.dto.user.UserLoginResponseDto;
import mateacademy.onlinebookstore.security.JwtUtil;
import mateacademy.onlinebookstore.service.user.AuthenticationService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @Override
    public UserLoginResponseDto login(UserLoginRequestDto requestDto) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(requestDto.getEmail(),
                        requestDto.getPassword()));
        String token = jwtUtil.generateToken(authenticate.getName());

        return new UserLoginResponseDto(token);
    }
}
