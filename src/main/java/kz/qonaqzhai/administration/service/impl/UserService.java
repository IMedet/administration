package kz.qonaqzhai.administration.service.impl;

import java.util.List;

import kz.qonaqzhai.administration.entity.User;
import kz.qonaqzhai.administration.repository.UserRepository;
import kz.qonaqzhai.administration.service.IUserService;
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

        return userRepository.findById(id).orElseThrow(()-> new CustomException("The user doesn't exist", HttpStatus.NOT_FOUND));
    }

}
