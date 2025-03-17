package ru.service.utilities.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Value;

@Value
public class AddMeterDTO {

    @NotBlank
    public int data;

    @NotBlank
    public String name;
}
