package ru.service.utilities.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.service.utilities.dto.request.AddAddressDTO;
import ru.service.utilities.dto.request.ClientUpdateDTO;
import ru.service.utilities.dto.response.AuthResponseDTO;
import ru.service.utilities.dto.request.ClientLoginDTO;
import ru.service.utilities.entity.AddressClient;
import ru.service.utilities.entity.Client;
import ru.service.utilities.repository.AddressClientRepository;
import ru.service.utilities.repository.ClientRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;
    private final AddressClientRepository addressClientRepository;
    private final OtpService otpService;
    private final NotificationService notificationService;
    private final JwtService jwtService;

    public void createCode(String email){
        Client client = clientRepository.findByEmail(email).orElseGet(
                () -> clientRepository.save(
                        Client.builder()
                                .email(email)
                                .build()
                )
        );
        String code = otpService.generateOtp(email);
        notificationService.emailCode(code);
        System.out.println(otpService.checkOtp(email, code));
    }

    public ResponseEntity<AuthResponseDTO> login(ClientLoginDTO dto){
        boolean isValid = otpService.checkOtp(dto.getEmail(), dto.getCode());
        if (!isValid) {
            return ResponseEntity.status(401).build();
        }

        Optional<Client> optionalClient = clientRepository.findByEmail(dto.getEmail());
        if (optionalClient.isEmpty()) {
            return ResponseEntity.status(404).build();
        }

        Client client = optionalClient.get();
        String accessToken = jwtService.generateAccessToken(client);

        return ResponseEntity.ok(new AuthResponseDTO(accessToken));
    }

    public ResponseEntity<String> updateClient(ClientUpdateDTO dto) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        Client client = clientRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Клиент не найден"));

        client.setFirstName(dto.firstName);
        client.setLastName(dto.lastName);
        client.setMiddleName(dto.middleName);
        client.setPhone(dto.phone);
        System.out.println("1");
        if (dto.address != null) {
            AddAddressDTO addressDTO = dto.address;
            System.out.println("2");
            AddressClient addressClient = client.getAddressClients().stream()
                    .findFirst()
                    .orElse(new AddressClient());

            addressClient.setTown(addressDTO.town);
            addressClient.setStreet(addressDTO.street);
            addressClient.setHouse(addressDTO.house);
            addressClient.setBuilding(addressDTO.building);
            addressClient.setApartment(addressDTO.apartment);
            addressClient.setClient(client);

            addressClientRepository.save(addressClient);
            System.out.println("3");
        }

        clientRepository.save(client);

        return ResponseEntity.ok("Данные пользователя были обнавлены");
    }
}
