package mateacademy.onlinebookstore.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class RegisterUserRequestDto {
    @NotBlank
    private String email;
    @NotBlank
    @Length(min = 4, max = 20)
    private String password;
    private String repeatPassword;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    private String shippingAddress;
}
