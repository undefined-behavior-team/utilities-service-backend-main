package ru.service.utilities.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.service.utilities.dto.request.AddAdminUserDTO;
import ru.service.utilities.dto.request.AddClientToHomeownerDTO;
import ru.service.utilities.service.AdminService;

@Tag(name = "Admin Controller", description = "Функционал администратора")
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @Operation(summary = "Добавляет администратора/ТСЖ", description = "Добавялет в БД нового администратора/ТСЖ")
    @PostMapping("/add_admin")
    public ResponseEntity<String> addUser(@RequestBody AddAdminUserDTO dto) {
        return adminService.addAdminUser(dto);
    }

    @Operation(summary = "Связывает ТСЖ и клиента", description = "Связывает ТСЖ и клиента по их email в БД")
    @PostMapping("/add_client_to_homeowner")
    public ResponseEntity<String> addClientToHomeOwner(@RequestBody AddClientToHomeownerDTO dto) {
        return adminService.addClientToHomeOwner(dto);
    }
}
