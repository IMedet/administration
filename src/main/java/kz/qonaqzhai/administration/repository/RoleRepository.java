package kz.qonaqzhai.administration.repository;

import kz.qonaqzhai.administration.entity.Role;
import kz.qonaqzhai.shared.dto.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(ERole role);
}
