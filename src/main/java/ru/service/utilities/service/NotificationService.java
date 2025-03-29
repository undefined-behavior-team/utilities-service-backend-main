package ru.service.utilities.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.service.utilities.dto.kafka.EmailMessage;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final KafkaProducerService kafkaProducerService;

    public void emailCode(String email, String code){
        kafkaProducerService.sendMailMessage(
                EmailMessage.builder()
                        .to(email)
                        .subject("Код для аккаунта ТСЖ")
                        .body("Код: " + code)
                        .build()
        );
    }
}
