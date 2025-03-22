package ru.service.utilities.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Value;

@Value
public class AdminLoginDTO {
    @NotBlank
    @Email
    public String email;

    @NotBlank
    public String password;
}
