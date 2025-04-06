package ru.service.utilities.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.service.utilities.dto.request.*;
import ru.service.utilities.dto.response.ApplicationResponseDTO;
import ru.service.utilities.dto.response.AuthResponseDTO;
import ru.service.utilities.dto.response.ClientPaymentDTO;
import ru.service.utilities.dto.response.MeterReadingDTO;
import ru.service.utilities.entity.*;
import ru.service.utilities.repository.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;
    private final AddressClientRepository addressClientRepository;
    private final OtpService otpService;
    private final NotificationService notificationService;
    private final JwtService jwtService;
    private final MeterReadingRepository meterReadingRepository;
    private final MeterRepository meterRepository;
    private final PaymentRepository paymentRepository;
    private final ApplicationRepository applicationRepository;
    private final ApplicationClientRepository applicationClientRepository;
    private final ClientHomeownerRepository clientHomeownerRepository;

    public void createCode(String email){
        Client client = clientRepository.findByEmail(email).orElseGet(
                () -> clientRepository.save(
                        Client.builder()
                                .email(email)
                                .build()
                )
        );
        String code = otpService.generateOtp(email);

        notificationService.emailCode(email, code);
    }


    public ResponseEntity<AuthResponseDTO> login(ClientLoginDTO dto){
        boolean isValid = otpService.checkOtp(dto.getEmail(), dto.getCode());
        if (!isValid) {
            return ResponseEntity.status(401).build();
        }

        Optional<Client> optionalClient = clientRepository.findByEmail(dto.getEmail());
        if (optionalClient.isEmpty()) {
            return ResponseEntity.status(404).build();
        }

        Client client = optionalClient.get();
        String accessToken = jwtService.generateAccessToken(client);

        return ResponseEntity.ok(new AuthResponseDTO(accessToken));
    }

    public ResponseEntity<String> updateClient(ClientUpdateDTO dto) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        Client client = clientRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Клиент не найден"));

        client.setFirstName(dto.firstName);
        client.setLastName(dto.lastName);
        client.setMiddleName(dto.middleName);
        client.setPhone(dto.phone);
        System.out.println("1");
        if (dto.address != null) {
            AddAddressDTO addressDTO = dto.address;
            System.out.println("2");
            AddressClient addressClient = client.getAddressClients().stream()
                    .findFirst()
                    .orElse(new AddressClient());

            addressClient.setTown(addressDTO.town);
            addressClient.setStreet(addressDTO.street);
            addressClient.setHouse(addressDTO.house);
            addressClient.setBuilding(addressDTO.building);
            addressClient.setApartment(addressDTO.apartment);
            addressClient.setClient(client);

            addressClientRepository.save(addressClient);
            System.out.println("3");
        }

        clientRepository.save(client);

        return ResponseEntity.ok("Данные пользователя были обнавлены");
    }

    public ResponseEntity<String> addMeterReading(AddMeterDTO dto) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        Client client = clientRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Клиент не найден"));

        Meter meter = new Meter();
        meter.setName(dto.name);
        meter.setData(dto.data);
        meterRepository.save(meter);

        MeterReading meterReading = new MeterReading();
        meterReading.setClient(client);
        meterReading.setMeter(meter);
        meterReading.setCreatedAt(LocalDate.now());

        meterReadingRepository.save(meterReading);

        return ResponseEntity.ok("Показания счетчика успешно добавлены");
    }

    public ResponseEntity<String> addPayment(AddPaymentDTO dto) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        Client client = clientRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Клиент не найден"));

        Payment payment = new Payment();

        payment.setClient(client);
        payment.setAmount(dto.amount);
        payment.setPaymentMethod(dto.paymentMethod);
        payment.setStatus(dto.status);
        payment.setCreatedAt(LocalDateTime.now());

        paymentRepository.save(payment);

        return ResponseEntity.ok("Платеж успешно добавлен");
    }

    public ResponseEntity<String> addApplication(AddApplicationDTO dto) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        Client client = clientRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Клиент не найден"));

        AddressClient address = addressClientRepository.findByClient(client)
                .orElseThrow(() -> new RuntimeException("Адрес клиента не найден"));

        ClientHomeowner clientHomeowner = clientHomeownerRepository.findByClient(client)
                .orElseThrow(() -> new RuntimeException("Связь с домовладельцем не найдена"));

        AdminUser adminUser = clientHomeowner.getAdminUser();

        Application application = Application.builder()
                .applicationType(dto.getApplicationType())
                .name(dto.getName())
                .description(dto.getDescription())
                .createdAt(LocalDateTime.now())
                .updatedAt(null)
                .status(0)
                .addressClient(address)
                .adminUser(adminUser)
                .build();


        applicationRepository.save(application);


        ApplicationClient applicationClient = ApplicationClient.builder()
                .client(client)
                .application(application)
                .build();

        applicationClientRepository.save(applicationClient);

        return ResponseEntity.ok("Заявка успешно добавлена");
    }

    public ResponseEntity<List<MeterReadingDTO>> getUserMeterReadings() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        Client client = clientRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Клиент не найден"));

        List<MeterReadingDTO> readings = meterReadingRepository.findByClient(client)
                .stream()
                .map(MeterReadingDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(readings);
    }

    public ResponseEntity<List<ApplicationResponseDTO>> getApplication(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Client client = clientRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Клиент не найден"));

        List<ApplicationResponseDTO> applications = applicationClientRepository.findByClient(client)
                .stream()
                .map(buffer -> new ApplicationResponseDTO(buffer.getApplication()))
                .toList();

        return ResponseEntity.ok(applications);
    }

    public ResponseEntity<ApplicationResponseDTO> getApplicationById(UUID applicationId){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Client client = clientRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Клиент не найден"));

        ApplicationClient applicationClient = applicationClientRepository.findByClientAndApplicationId(client, applicationId)
                .orElseThrow(() -> new RuntimeException("Заявка не найдена или не принадлежит клиенту"));

        ApplicationResponseDTO responseDTO = new ApplicationResponseDTO(applicationClient.getApplication());
        return ResponseEntity.ok(responseDTO);
    }

    public ResponseEntity<ClientUpdateDTO> getClient(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        Client client = clientRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Клиент не найден"));

        AddressClient addressClient = addressClientRepository.findByClient(client)
                .orElseThrow(() -> new RuntimeException("Адрес клиента не найден"));

        AddAddressDTO addressDTO = new AddAddressDTO(
                addressClient.getTown(),
                addressClient.getStreet(),
                addressClient.getHouse(),
                addressClient.getBuilding(),
                addressClient.getApartment()
        );

        ClientUpdateDTO clientUpdateDTO = new ClientUpdateDTO(
                client.getFirstName(),
                client.getLastName(),
                client.getMiddleName(),
                client.getPhone(),
                addressDTO
        );

        return ResponseEntity.ok(clientUpdateDTO);
    }

    public ResponseEntity<List<ClientPaymentDTO>> getPayment(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        Client client = clientRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Клиент не найден"));

        List<ClientPaymentDTO> payments = paymentRepository.findByClient(client)
                .stream()
                .map(ClientPaymentDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(payments);
    }
}
