package ru.service.utilities.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.service.utilities.entity.Client;
import ru.service.utilities.entity.Role;
import ru.service.utilities.entity.UserInterface;
import ru.service.utilities.repository.AdminUserRepository;
import ru.service.utilities.repository.ClientRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final AdminUserRepository adminUserRepository;
    private final ClientRepository clientRepository;

    public static Optional<UserInterface> getCurrentUser() {
        // Получение имени пользователя из контекста Spring Security
        return Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .filter(Authentication::isAuthenticated)
                .map(Authentication::getPrincipal)
                .filter(principal -> principal instanceof UserInterface)
                .map(UserInterface.class::cast);
    }



    public UserInterface getByUsernameAndRole(String username, Role role) throws UsernameNotFoundException {
        if(Role.CLIENT.equals(role)){
            return clientRepository.findByEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException("Bad credentials"));
        }
        if(Role.UNKNOWN.equals(role)){
            throw new UsernameNotFoundException("Bad credentials");
        }

        return adminUserRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Bad credentials"));
    }
    public UserInterface getByUsername(String username) throws UsernameNotFoundException {

        Optional<Client> userInterface = clientRepository.findByEmail(username);
        if(userInterface.isPresent()){
            return userInterface.get();
        }
        return adminUserRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Bad credentials"));
    }

    public UserDetailsService userDetailsService() {
        return this::getByUsername;
    }
}
