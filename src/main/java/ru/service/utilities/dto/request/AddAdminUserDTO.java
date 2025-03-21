package ru.service.utilities.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Value
public class AddAdminUserDTO {
    @Email
    @NotBlank
    public String email;

    public boolean isAdmin;

    @NotBlank
    public String name;

    @NotBlank
    public String password;

    @NotBlank
    public String address;

    @NotBlank
    public String phone;
}
