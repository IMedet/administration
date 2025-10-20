package kz.qonaqzhai.administration.service.impl;

import java.util.*;

import jakarta.transaction.Transactional;
import kz.qonaqzhai.administration.entity.Role;
import kz.qonaqzhai.administration.entity.User;
import kz.qonaqzhai.administration.mapper.UserMapper;
import kz.qonaqzhai.administration.repository.RoleRepository;
import kz.qonaqzhai.administration.repository.UserRepository;
import kz.qonaqzhai.administration.service.IUserService;
import kz.qonaqzhai.shared.dto.UserCreateRequest;
import kz.qonaqzhai.shared.dto.UserDto;
import kz.qonaqzhai.shared.dto.UserResponseDTO;
import kz.qonaqzhai.shared.dto.enums.ERole;
import kz.qonaqzhai.shared.exceptions.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    @Override
    public UserResponseDTO createUser(UserCreateRequest request) {
        log.info("Creating user: {}", request.getUsername());

        if (userRepository.existsUserByUsername(request.getUsername())) {
            throw new CustomException("User already exists", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setIin(request.getIin());
        user.setRole(request.getRole());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setFio(request.getFio());

        // Обработка ролей
        if (request.getRoleNames() != null && !request.getRoleNames().isEmpty()) {
            log.info("Processing {} roles", request.getRoleNames().size());
            Set<Role> managedRoles = new HashSet<>();

            for (ERole roleName : request.getRoleNames()) {
                log.info("Processing role: {}", roleName);

                // Ищем существующую роль или создаем новую
                Role managedRole = roleRepository.findByRoleName(roleName)
                        .orElseGet(() -> {
                            log.info("Creating new role: {}", roleName);
                            Role newRole = new Role(roleName);
                            return roleRepository.save(newRole);
                        });

                log.info("Managed role ID: {}, Name: {}", managedRole.getId(), managedRole.getRoleName());
                managedRoles.add(managedRole);
            }
            user.setRoles(managedRoles);
        } else {
            user.setRoles(new HashSet<>());
        }

        log.info("Saving user with {} managed roles", user.getRoles().size());
        User savedUser = userRepository.save(user);
        log.info("User saved successfully with ID: {}", savedUser.getId());

        return UserResponseDTO.builder()
                .id(savedUser.getId())
                .username(savedUser.getUsername())
                .iin(savedUser.getIin())
                .role(savedUser.getRole())
                .roles(savedUser.getRoles().stream()
                        .map(Role::getRoleName)
                        .toList())
                .phoneNumber(savedUser.getPhoneNumber())
                .fio(savedUser.getFio())
                .build();
    }

    @Override
    public User getUserById(Long id) {

        return userRepository.findById(id)
                .orElseThrow(() -> new CustomException("The user doesn't exist", HttpStatus.NOT_FOUND));
    }

    @Override
    public UserDto getUserByIdNew(Long id) {
        User user = userRepository.findById(id).orElseThrow();
        UserDto userDto = UserMapper.INSTANCE.toUserTransmissionDto(user);
        return userDto;
    }

    @Override
    public UserDto getUserByIinNew(String iin) {
        User user = userRepository.findByIin(iin).orElseThrow();
        return UserMapper.INSTANCE.toUserTransmissionDto(user);
    }

    @Override
    public UserDto getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(UserService::toDto)
                .orElseThrow(() -> new CustomException("User not found with userName: " + username, HttpStatus.NOT_FOUND));
    }

    @Override
    public List<Role> getRoles() {
        return roleRepository.findAll();
    }

    @Override
    public UserDto getUserByPhoneNumber(String phoneNumber) {
        User user = userRepository.findByPhoneNumber(phoneNumber);
        return UserMapper.INSTANCE.toUserTransmissionDto(user);
    }

    @Override
    public List<User> getUsersByRole(String roleStr) {
        ERole role = ERole.valueOf(roleStr);
        return userRepository.findUsersByRole(role);
    }

    @Override
    public void delete(String username) {
        userRepository.deleteByUsername(username);
    }

    private static UserDto toDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .iin(user.getIin())
                .role(user.getRole())
                .roles(Optional.ofNullable(user.getRoles())
                        .map(set -> set.stream()
                                .map(Role::getRoleName).toList())
                        .orElse(Collections.emptyList()))
                .phoneNumber(user.getPhoneNumber())
                .fio(user.getFio())
                .build();
    }
}
