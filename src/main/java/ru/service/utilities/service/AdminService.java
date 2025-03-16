package ru.service.utilities.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.service.utilities.dto.request.AddAdminUserDTO;
import ru.service.utilities.dto.request.AddClientToHomeownerDTO;
import ru.service.utilities.dto.request.AdminLoginDTO;
import ru.service.utilities.dto.response.AuthResponseDTO;
import ru.service.utilities.entity.AdminUser;
import ru.service.utilities.entity.Client;
import ru.service.utilities.entity.ClientHomeowner;
import ru.service.utilities.repository.AdminUserRepository;
import ru.service.utilities.repository.ClientHomeownerRepository;
import ru.service.utilities.repository.ClientRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdminUserRepository adminUserRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final ClientRepository clientRepository;
    private final ClientHomeownerRepository clientHomeownerRepository;;

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

    public ResponseEntity<String> addAdminUser(AddAdminUserDTO dto) {
        if (adminUserRepository.findByEmail(dto.getEmail()).isPresent() || clientRepository.findByEmail(dto.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Пользователь с таким email уже существует");
        }

        String encodedPassword = passwordEncoder.encode(dto.password);

        AdminUser newUser = AdminUser.builder()
                .email(dto.email)
                .isAdmin(dto.isAdmin)
                .name(dto.name)
                .password(encodedPassword)
                .address(dto.address)
                .phone(dto.phone)
                .build();

        adminUserRepository.save(newUser);

        return ResponseEntity.ok("Пользователь успешно добавлен");
    }

    public ResponseEntity<String> addClientToHomeOwner(AddClientToHomeownerDTO dto) {
        Optional<AdminUser> adminUserOpt = adminUserRepository.findByEmail(dto.emailHomeOwner);
        Optional<Client> clientOpt = clientRepository.findByEmail(dto.emailClient);

        if (adminUserOpt.isEmpty() || clientOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Один из пользователей не найден");
        }

        AdminUser homeowner = adminUserOpt.get();
        Client client = clientOpt.get();

        ClientHomeowner clientHomeowner = ClientHomeowner.builder()
                .adminUser(homeowner)
                .client(client)
                .build();

        clientHomeownerRepository.save(clientHomeowner);

        return ResponseEntity.ok("Пользователь был прикреплен к ТСЖ");
    }
}
