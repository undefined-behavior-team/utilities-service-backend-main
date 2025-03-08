package ru.service.utilities.dto.response;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Value;

@Value
public class AdminLoginDTO {
    @NotBlank
    @Email
    String email;
    @NotBlank
    String password;
}
