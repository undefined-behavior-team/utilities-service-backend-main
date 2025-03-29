package ru.service.utilities.dto.response;

import lombok.Value;
import ru.service.utilities.entity.Payment;

import java.time.LocalDateTime;

@Value
public class ClientPaymentDTO {
    
    public int amount;
    
    public String paymentMethod;
    
    public String status;
    
    public LocalDateTime createdAt;

    public ClientPaymentDTO(Payment payment){
        this.amount = payment.getAmount();
        this.paymentMethod = payment.getPaymentMethod();
        this.status = payment.getStatus();
        this.createdAt = payment.getCreatedAt();
    }
}
