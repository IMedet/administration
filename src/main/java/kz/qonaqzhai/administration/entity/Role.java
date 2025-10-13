package kz.qonaqzhai.administration.entity;

import com.fasterxml.jackson.annotation.JsonCreator;

import jakarta.persistence.*;
import kz.qonaqzhai.shared.dto.enums.ERole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "roles")
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private ERole roleName;

    public Role(ERole roleName) {
        this.roleName = roleName;
    }

    @JsonCreator
    public static Role fromString(String value) {
        Role role = new Role();
        role.setRoleName(ERole.valueOf(value));
        return role;
    }

}
