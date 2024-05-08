package mateacademy.onlinebookstore.service.impl;

import java.util.Set;
import lombok.RequiredArgsConstructor;
import mateacademy.onlinebookstore.dto.user.RegisterUserRequestDto;
import mateacademy.onlinebookstore.dto.user.UserResponseDto;
import mateacademy.onlinebookstore.exception.RegistrationException;
import mateacademy.onlinebookstore.mapper.UserRegistrationMapper;
import mateacademy.onlinebookstore.model.Role;
import mateacademy.onlinebookstore.model.User;
import mateacademy.onlinebookstore.repository.role.RoleRepository;
import mateacademy.onlinebookstore.repository.user.UserRepository;
import mateacademy.onlinebookstore.service.user.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private static final String ROLE = "USER";
    private final UserRepository userRepository;
    private final UserRegistrationMapper mapper;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponseDto register(RegisterUserRequestDto userRequestDto)
            throws RegistrationException {
        if (!isPasswordSame(userRequestDto)) {
            throw new RegistrationException("The fields password and repeatPassword"
                    + " are not the same");
        }
        if (userRepository.findByEmail(userRequestDto.getEmail()).isPresent()) {
            throw new RegistrationException("User already exists with this email: "
                    + userRequestDto.getEmail());
        }
        User user = mapper.toModel(userRequestDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Set.of(roleRepository.findByRoleName(Role.RoleName.valueOf(ROLE))));
        return mapper.toDto(userRepository.save(user));
    }

    private boolean isPasswordSame(RegisterUserRequestDto userRequestDto) {
        return userRequestDto.getPassword().equals(userRequestDto.getRepeatPassword());
    }
}
