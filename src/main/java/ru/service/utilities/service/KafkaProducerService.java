package ru.service.utilities.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.service.utilities.dto.kafka.EmailMessage;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaProducerService {
    private final KafkaTemplate<String, EmailMessage> kafkaTemplate;

    @Value("${kafka-topics.mail}")
    private String kafkaMailTopic;

    public void sendMailMessage(EmailMessage message) {
        kafkaTemplate.send(kafkaMailTopic, message)
                .thenAccept(result -> {
                    log.info("Successfully sent message to Kafka. Topic: {}, Partition: {}, Offset: {}",
                            kafkaMailTopic,
                            result.getRecordMetadata().partition(),
                            result.getRecordMetadata().offset());

                    log.debug("Message details - To: {}, Subject: {}",
                            message.getTo(),
                            message.getSubject());
                }).exceptionally(ex -> {
                    log.error("Failed to send message to Kafka. Topic: {}", kafkaMailTopic, ex);
                    log.warn("Failed message details - To: {}, Subject: {}",
                            message.getTo(),
                            message.getSubject());
                    return null;
                });
    }
}
