package ru.service.utilities.dto.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.Value;

@Value
public class ClientUpdateDTO {
    @NotBlank
    public String firstName;

    @NotBlank
    public String lastName;

    @NotBlank
    public String middleName;

    @NotBlank
    public String phone;

    public AddAddressDTO address;
}
