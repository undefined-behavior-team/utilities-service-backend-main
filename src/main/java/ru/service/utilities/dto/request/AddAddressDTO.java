package ru.service.utilities.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Value;

@Value
public class AddAddressDTO {
    @NotBlank
    public String town;

    @NotBlank
    public String street;

    @NotBlank
    public String house;

    public String building;

    @NotBlank
    public String apartment;
}
