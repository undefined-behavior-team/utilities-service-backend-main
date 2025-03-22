package ru.service.utilities.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Value;

@Value
public class AddApplicationDTO {

    public short applicationType;

    @NotBlank
    public String name;

    @NotBlank
    public String description;
}
