package ru.service.utilities.service;

import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    public void emailCode(String code){
        System.out.println("Код для входа: " + code);
    }
}
