package kz.qonaqzhai.administration.service.impl;

import java.lang.StackWalker.Option;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import kz.qonaqzhai.administration.entity.Role;
import kz.qonaqzhai.administration.entity.User;
import kz.qonaqzhai.administration.mapper.UserMapper;
import kz.qonaqzhai.administration.repository.UserRepository;
import kz.qonaqzhai.administration.service.IUserService;
import kz.qonaqzhai.shared.dto.UserDto;
import kz.qonaqzhai.shared.exceptions.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {

        return userRepository.findAll();
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
        UserDto userDto = UserMapper.INSTANCE.toUserTransmissionDto(user);
        return userDto;
    }

    @Override
    public UserDto getUserByUsername(String username) {
        return toDto(userRepository.findByUsername(username));
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
