package ru.service.utilities.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.service.utilities.dto.request.*;
import ru.service.utilities.dto.response.ApplicationResponseDTO;
import ru.service.utilities.dto.response.ClientPaymentDTO;
import ru.service.utilities.dto.response.MeterReadingDTO;
import ru.service.utilities.service.ClientService;

import java.util.List;
import java.util.UUID;

@Tag(name = "Client Controller", description = "Основной функционал клиента")
@RestController
@RequestMapping("/client")
@RequiredArgsConstructor
public class ClientController {
    private final ClientService clientService;

    @Operation(summary = "Обновляет информации о клиенте", description = "Обновляет информацию о клиента включая его адрес")
    @PutMapping("/update_data")
    public ResponseEntity<String> updateClient(@RequestBody ClientUpdateDTO dto) {
        return clientService.updateClient(dto);
    }

    @Operation(summary = "Отправление показаний", description = "Отправляет показания счетчика (горячая, холодная, электричество, газ)")
    @PostMapping("/add_meter")
    public ResponseEntity<String> addMeter(@RequestBody AddMeterDTO dto) {
        return clientService.addMeterReading(dto);
    }

    @Operation(summary = "Имитация оплаты (сделана плохо)", description = "Просто добавляет в БД оплату и всё))")
    @PostMapping("/add_payment")
    public ResponseEntity<String> addPayment(@RequestBody AddPaymentDTO dto) {
        return clientService.addPayment(dto);
    }

    @Operation(summary = "Добавление заявок", description = "Добавляет заявку, созданную клиентом")
    @PostMapping("/add_application")
    public ResponseEntity<String> addApplication(@RequestBody AddApplicationDTO dto) {
        return clientService.addApplication(dto);
    }

    @Operation(summary = "История показаний", description = "Выводит историю показания клиента")
    @GetMapping("/get_history_meter_reading")
    public ResponseEntity<List<MeterReadingDTO>> getMeterRedding() {
        return clientService.getUserMeterReadings();
    }

    @Operation(summary = "Все заявки клиента", description = "Выводит заявки клиента, которые он отправлял в ТСЖ")
    @GetMapping("/get_application")
    public ResponseEntity<List<ApplicationResponseDTO>> getApplication() {
        return clientService.getApplication();
    }

    @Operation(summary = "Определенная заявка по UUID", description = "Выводит определенную заявку по её UUID")
    @GetMapping("/get_application/{uuid}")
    public ResponseEntity<ApplicationResponseDTO> getApplication(@PathVariable UUID uuid) {
        return clientService.getApplicationById(uuid);
    }

    @Operation(summary = "Выводит информацию о клиенте", description = "Выводит информацию о клиенте (Предположил, что ты будешь её использовать во время изменения данных о клиенте, чтобы заранее заполнить поля, которые уже заполнены)")
    @GetMapping("/get_client_info")
    public ResponseEntity<ClientUpdateDTO> getClient(){
        return clientService.getClient();
    }

    @Operation(summary = "Выводит информацию о платежах", description = "Выводит информацию о всех платежах клиента")
    @GetMapping("/get_client_payment")
    public ResponseEntity<List<ClientPaymentDTO>> getClientPayment(){
        return clientService.getPayment();
    }

}
