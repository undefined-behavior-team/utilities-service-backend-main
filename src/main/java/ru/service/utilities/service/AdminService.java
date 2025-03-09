package ru.service.utilities.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.service.utilities.dto.response.AdminLoginDTO;
import ru.service.utilities.dto.response.AuthResponseDTO;
import ru.service.utilities.entity.AdminUser;
import ru.service.utilities.repository.AdminUserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdminUserRepository adminUserRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    
    public ResponseEntity<AuthResponseDTO> login(AdminLoginDTO dto) {
        Optional<AdminUser> optionalAdminUser = adminUserRepository.findByEmail(dto.getEmail());
        if (optionalAdminUser.isEmpty()) {
            return ResponseEntity.status(401).body(new AuthResponseDTO("Не верный логин или пароль"));
        }

        AdminUser adminUser = optionalAdminUser.get();

        if (!passwordEncoder.matches(dto.getPassword(), adminUser.getPassword())) {
            return ResponseEntity.status(401).body(new AuthResponseDTO("Не верный логин или пароль"));
        }

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword());
        authenticationManager.authenticate(authenticationToken);

        String accessToken = jwtService.generateAccessToken(adminUser);

        return ResponseEntity.ok(new AuthResponseDTO(accessToken));
    }
}
