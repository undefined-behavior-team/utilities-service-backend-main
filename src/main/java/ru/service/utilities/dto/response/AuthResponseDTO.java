package ru.service.utilities.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor
public class AuthResponseDTO {
    public String accessToken;
}
