package ru.service.utilities.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.service.utilities.dto.request.ClientSignDTO;
import ru.service.utilities.dto.request.AdminLoginDTO;
import ru.service.utilities.dto.response.AuthResponseDTO;
import ru.service.utilities.dto.request.ClientLoginDTO;
import ru.service.utilities.service.AdminService;
import ru.service.utilities.service.ClientService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final ClientService clientService;
    private final AdminService adminService;

    @PostMapping("/client")
    public void client(@RequestBody @Valid ClientSignDTO dto){
        clientService.createCode(dto.email);
    }

    @PostMapping("/client/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody @Valid ClientLoginDTO dto) {
        return clientService.login(dto);
    }

    @PostMapping("/admin/login")
    public ResponseEntity<AuthResponseDTO> loginAdmin(@RequestBody @Valid AdminLoginDTO dto) {
        return adminService.login(dto);
    }
}
