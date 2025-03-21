package ru.service.utilities.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.service.utilities.dto.request.ApplicationUpdateDTO;
import ru.service.utilities.dto.response.ApplicationResponseDTO;
import ru.service.utilities.entity.AdminUser;
import ru.service.utilities.entity.Application;
import ru.service.utilities.repository.AdminUserRepository;
import ru.service.utilities.repository.ApplicationRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HomeOwnerService {
    private final AdminUserRepository adminUserRepository;
    private final ApplicationRepository applicationRepository;


    public ResponseEntity<List<ApplicationResponseDTO>> getApplication() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        AdminUser adminUser = adminUserRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Администратор не найден"));

        List<Application> applications = applicationRepository.findByAdminUser(adminUser);

        List<ApplicationResponseDTO> responseDTOList = applications.stream()
                .map(ApplicationResponseDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(responseDTOList);
    }

    public ResponseEntity<ApplicationResponseDTO> getApplicationById(UUID uuid) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        AdminUser adminUser = adminUserRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Администратор не найден"));

        Application application = applicationRepository.findByIdAndAdminUser(uuid, adminUser)
                .orElseThrow(() -> new RuntimeException("Заявка не найдена или не принадлежит администратору"));

        ApplicationResponseDTO responseDTO = new ApplicationResponseDTO(application);

        return ResponseEntity.ok(responseDTO);
    }

    public ResponseEntity<ApplicationResponseDTO> updateApplication(UUID applicationId, ApplicationUpdateDTO updateDTO) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        AdminUser adminUser = adminUserRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Администратор не найден"));

        Application application = applicationRepository.findByIdAndAdminUser(applicationId, adminUser)
                .orElseThrow(() -> new RuntimeException("Заявка не найдена или не принадлежит администратору"));

        application.setName(updateDTO.getName());
        application.setDescription(updateDTO.getDescription());
        application.setStatus(updateDTO.getStatus());
        application.setUpdatedAt(LocalDateTime.now());

        applicationRepository.save(application);

        ApplicationResponseDTO responseDTO = new ApplicationResponseDTO(application);
        return ResponseEntity.ok(responseDTO);
    }
}
