package ru.service.utilities.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.service.utilities.dto.request.AddApplicationDTO;
import ru.service.utilities.dto.request.AddMeterDTO;
import ru.service.utilities.dto.request.AddPaymentDTO;
import ru.service.utilities.dto.request.ClientUpdateDTO;
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

    @PostMapping("/add_meter")
    public ResponseEntity<String> addMeter(@RequestBody AddMeterDTO dto) {
        return clientService.addMeterReading(dto);
    }

    @PostMapping("/add_payment")
    public ResponseEntity<String> addPayment(@RequestBody AddPaymentDTO dto) {
        return clientService.addPayment(dto);
    }

    @PostMapping("/add_application")
    public ResponseEntity<String> addApplication(@RequestBody AddApplicationDTO dto) {
        return clientService.addApplication(dto);
    }
}
