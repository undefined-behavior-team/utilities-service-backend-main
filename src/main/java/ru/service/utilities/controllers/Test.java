package ru.service.utilities.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.service.utilities.dto.response.AddressClientDto;
import ru.service.utilities.entity.Client;
import ru.service.utilities.repository.ClientRepository;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class Test {
    private final ClientRepository clientRepository;

    @GetMapping("/testing/{email}")
    public Object client(@PathVariable String email){
        return clientRepository.findByEmail(email).map(client -> client.getAddressClients().stream().map(AddressClientDto::new));
    }

}
