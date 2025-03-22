package ru.service.utilities.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "Authentication controller", description = "Авторизация")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final ClientService clientService;
    private final AdminService adminService;

    @Operation(summary = "Генерация кода для клиента", description = "Генерирует код для авторизации клиента")
    @PostMapping("/client")
    public void client(@RequestBody @Valid ClientSignDTO dto){
        clientService.createCode(dto.email);
    }

    @Operation(summary = "Авторизация клиента", description = "Авторизирует клиента и генерирует JWT токен")
    @PostMapping("/client/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody @Valid ClientLoginDTO dto) {
        return clientService.login(dto);
    }

    @Operation(summary = "Авторизация Админма/ТСЖ", description = "Авторизирует ТСЖ/Админа и генерирует JWT токен")
    @PostMapping("/admin/login")
    public ResponseEntity<AuthResponseDTO> loginAdmin(@RequestBody @Valid AdminLoginDTO dto) {
        return adminService.login(dto);
    }
}
