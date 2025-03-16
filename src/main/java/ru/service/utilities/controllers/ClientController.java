package ru.service.utilities.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.service.utilities.dto.request.ClientUpdateDTO;
import ru.service.utilities.entity.Client;
import ru.service.utilities.service.ClientService;

@RestController
@RequestMapping("/client")
@RequiredArgsConstructor
public class ClientController {
    private final ClientService clientService;

    @PutMapping("/update_data")
    public ResponseEntity<String> updateClient(@RequestBody ClientUpdateDTO dto) {
        return clientService.updateClient(dto);
    }
}
