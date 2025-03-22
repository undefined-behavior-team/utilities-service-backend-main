package ru.service.utilities.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Value;

@Value
public class AddPaymentDTO {
    @NotBlank
    public String paymentMethod;

    public int amount;

    @NotBlank
    public String status;
}
