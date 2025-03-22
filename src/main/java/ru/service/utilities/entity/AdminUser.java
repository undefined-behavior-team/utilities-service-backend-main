package ru.service.utilities.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "homeowner_user")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminUser implements UserInterface {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(length = 150)
    private String password;

    @Column(length = 150, nullable = false)
    private String name;

    @Column(length = 19)
    private String phone;

    @Column(columnDefinition = "TEXT")
    private String address;

    @Column(name = "admin", nullable = false)
    private Boolean isAdmin;

    @OneToMany(mappedBy = "adminUser", fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private List<ClientHomeowner> clientAdminUsers;

    public Role getRole(){
        return isAdmin ? Role.ADMIN : Role.HOMEOWNER;
    }

    @Override
    public String getUsername(){
        return getEmail();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRole().getAuthorities();
    }
}
