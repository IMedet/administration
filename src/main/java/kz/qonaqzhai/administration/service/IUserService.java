package kz.qonaqzhai.administration.service;

import kz.qonaqzhai.administration.entity.User;
import kz.qonaqzhai.shared.dto.UserDto;

import java.util.List;

public interface IUserService {
    List<User> getAllUsers();

    User getUserById(Long id);
    
    UserDto getUserByIdNew(Long id);

    UserDto getUserByIinNew(String iin);

    UserDto getUserByUsername(String username);

    List<User> getUsersByRole(String role);

    void delete(String username);
}
