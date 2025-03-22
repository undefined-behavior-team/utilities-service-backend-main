package ru.service.utilities.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.service.utilities.dto.request.ApplicationUpdateDTO;
import ru.service.utilities.dto.response.ApplicationResponseDTO;
import ru.service.utilities.service.HomeOwnerService;

import java.util.List;
import java.util.UUID;

@Tag(name = "HomeOwner Controller", description = "Основной функционал ТСЖ")
@RestController
@RequestMapping("/homeowner")
@RequiredArgsConstructor
public class HomeOwnerController {
    private final HomeOwnerService homeOwnerService;

    @Operation(summary = "Выводит все заявки в ТСЖ", description = "Выводит все заявки, которые клиенты писали в ТСЖ")
    @GetMapping("/get_application")
    public ResponseEntity<List<ApplicationResponseDTO>> getApplications() {
        return homeOwnerService.getApplication();
    }

    @Operation(summary = "Заявка по UUID", description = "Выводит определенную заявку по её UUID")
    @GetMapping("/get_application/{uuid}")
    public ResponseEntity<ApplicationResponseDTO> getApplication(@PathVariable UUID uuid) {
        return homeOwnerService.getApplicationById(uuid);
    }

    @Operation(summary = "Обновляет заявку по UUID", description = "Обновляет заявку по её UUID")
    @PutMapping("/update_application/{uuid}")
    public ResponseEntity<ApplicationResponseDTO> getApplication( @PathVariable UUID uuid, @RequestBody @Valid ApplicationUpdateDTO updateDTO) {
        return homeOwnerService.updateApplication(uuid, updateDTO);
    }
}
