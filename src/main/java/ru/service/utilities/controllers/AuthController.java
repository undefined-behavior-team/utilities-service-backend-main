package ru.service.utilities.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.service.utilities.dto.request.ClientSignDTO;
import ru.service.utilities.service.ClientService;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final PasswordEncoder passwordEncoder;

    private final ClientService clientService;

    @PostMapping("/client")
    public void client(@RequestBody @Valid ClientSignDTO dto){
        clientService.createCode(dto.email);
    }
}
