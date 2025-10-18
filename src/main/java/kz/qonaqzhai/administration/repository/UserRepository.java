package kz.qonaqzhai.administration.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import kz.qonaqzhai.administration.entity.User;
import kz.qonaqzhai.shared.dto.enums.ERole;

import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    List<User> findAll();
    Optional<User> findByIin(String iin);
    Optional<User> findByUsername(String username);
    List<User> findUsersByRole(ERole role);

    @Transactional
    void deleteByUsername(String username);

    User findByPhoneNumber(String phoneNumber);

    boolean existsUserByUsername(String username);
}
