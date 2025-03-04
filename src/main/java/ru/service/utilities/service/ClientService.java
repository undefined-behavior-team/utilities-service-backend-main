package ru.service.utilities.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import ru.service.utilities.entity.Client;
import ru.service.utilities.repository.ClientRepository;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;
    private final OtpService otpService;
    private final NotificationService notificationService;

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
}
