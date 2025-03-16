package ru.service.utilities.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.service.utilities.dto.request.AddAdminUserDTO;
import ru.service.utilities.dto.request.AddClientToHomeownerDTO;
import ru.service.utilities.service.AdminService;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @PostMapping("/add_admin")
    public ResponseEntity<String> addUser(@RequestBody AddAdminUserDTO dto) {
        return adminService.addAdminUser(dto);
    }

    @PostMapping("/add_client_to_homeowner")
    public ResponseEntity<String> addClientToHomeOwner(@RequestBody AddClientToHomeownerDTO dto) {
        return adminService.addClientToHomeOwner(dto);
    }
}
