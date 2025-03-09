package ru.service.utilities.entity;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Table (name = "client")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Client implements UserInterface {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(length = 50)
    private String firstName;

    @Column(length = 50)
    private String lastName;

    @Column(length = 50)
    private String middleName;

    @Column(length = 19)
    private String phone;

    @OneToMany(targetEntity = AddressClient.class,fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private List<AddressClient> addressClients;

    public Role getRole(){
        return Role.CLIENT;
    }

    @Override
    public String getUsername(){
        return getEmail();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Role.CLIENT.getAuthorities();
    }

    @Override
    public String getPassword() {
        return "";
    }

}
