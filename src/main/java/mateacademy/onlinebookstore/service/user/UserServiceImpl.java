package mateacademy.onlinebookstore.service.user;

import lombok.RequiredArgsConstructor;
import mateacademy.onlinebookstore.dto.user.RegisterUserRequestDto;
import mateacademy.onlinebookstore.dto.user.UserResponseDto;
import mateacademy.onlinebookstore.exception.RegistrationException;
import mateacademy.onlinebookstore.mapper.UserRegistrationMapper;
import mateacademy.onlinebookstore.model.User;
import mateacademy.onlinebookstore.repository.user.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserRegistrationMapper mapper;

    @Override
    public UserResponseDto register(RegisterUserRequestDto userRequestDto)
            throws RegistrationException {
        if (userRepository.findByEmail(userRequestDto.getEmail()).isPresent()) {
            throw new RegistrationException("Can't register this user");
        }
        if (checkPassword(userRequestDto.getPassword(), userRequestDto.getRepeatPassword())) {
            User user = mapper.toModel(userRequestDto);

            return mapper.toDto(userRepository.save(user));
        }
        throw new RegistrationException("The fields password and repeatPassword are not the same");
    }

    private boolean checkPassword(String password, String repeatPassword) {
        return password.equals(repeatPassword);
    }
}
