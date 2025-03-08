package ru.service.utilities.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.service.utilities.dto.response.AuthResponseDTO;
import ru.service.utilities.dto.response.ClientLoginDTO;
import ru.service.utilities.entity.Client;
import ru.service.utilities.repository.ClientRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;
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
}
