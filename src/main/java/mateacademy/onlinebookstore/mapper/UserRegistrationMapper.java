package mateacademy.onlinebookstore.mapper;

import mateacademy.onlinebookstore.config.MapperConfig;
import mateacademy.onlinebookstore.dto.user.RegisterUserRequestDto;
import mateacademy.onlinebookstore.dto.user.UserResponseDto;
import mateacademy.onlinebookstore.model.User;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface UserRegistrationMapper {
    UserResponseDto toDto(User user);

    User toModel(RegisterUserRequestDto userRequestDto);
}
