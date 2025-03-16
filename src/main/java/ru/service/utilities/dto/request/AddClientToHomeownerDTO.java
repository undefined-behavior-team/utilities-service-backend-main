package ru.service.utilities.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Value
public class AddClientToHomeownerDTO {
    @NotBlank
    @Email
    public String emailHomeOwner;

    @NotBlank
    @Email
    public String emailClient;
}
