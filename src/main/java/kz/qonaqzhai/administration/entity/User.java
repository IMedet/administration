package kz.qonaqzhai.administration.entity;

import java.util.Set;

import jakarta.persistence.*;
import kz.qonaqzhai.shared.dto.enums.ERole;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String iin;
    @Enumerated(EnumType.STRING)
    private ERole role;
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    //Applicant
    private String phoneNumber;
    private String fio;
}