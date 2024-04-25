package mateacademy.onlinebookstore.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserLoginRequestDto {
    @Email
    private String email;
    @NotEmpty
    @Size(min = 4, max = 20)
    private String password;
}
