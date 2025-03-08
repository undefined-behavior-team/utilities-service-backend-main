package ru.service.utilities.dto.response;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Value;

@Value
public class ClientLoginDTO {
    @NotBlank
    @Email
    public String email;

    @NotBlank
    public String code;
}
