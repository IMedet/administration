package kz.qonaqzhai.administration.service;

import kz.qonaqzhai.administration.entity.User;

import java.util.List;

public interface IUserService {
    List<User> getAllUsers();

    User getUserById(Long id);
}
