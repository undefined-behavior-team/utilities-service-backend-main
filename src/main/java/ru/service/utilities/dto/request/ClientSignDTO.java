package ru.service.utilities.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Value;

@Value
public class ClientSignDTO {
    @NotBlank
    @Email
    public String email;
}
