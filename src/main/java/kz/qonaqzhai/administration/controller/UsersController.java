package kz.qonaqzhai.administration.controller;

import jakarta.validation.Valid;
import kz.qonaqzhai.shared.dto.UserCreateRequest;
import kz.qonaqzhai.shared.dto.UserResponseDTO;
import kz.qonaqzhai.shared.exceptions.CustomException;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import kz.qonaqzhai.administration.entity.User;
import kz.qonaqzhai.administration.service.IUserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UsersController {

    private final IUserService userService;

    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(this.userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(this.userService.getUserById(id));
    }


    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUserByIdNew(@PathVariable Long id) {
        return ResponseEntity.ok(this.userService.getUserByIdNew(id));
    }

    @GetMapping("/user/iin/{iin}")
    public ResponseEntity<?> getUserByIinNew(@PathVariable String iin) {
        return ResponseEntity.ok(this.userService.getUserByIinNew(iin));
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<?> getUserByUsername(@PathVariable String username) {
        return ResponseEntity.ok(this.userService.getUserByUsername(username));
    }

    @GetMapping("/{role}/role")
    public ResponseEntity<List<User>> getUsersByRole(@PathVariable String role) {
        return ResponseEntity.ok(this.userService.getUsersByRole(role));
    }

    @GetMapping("/user/{phoneNumber}")
    public ResponseEntity<?> getUserByPhoneNumber(@PathVariable String phoneNumber) {
        return ResponseEntity.ok(this.userService.getUserByPhoneNumber(phoneNumber));
    }

    @GetMapping("/roles")
    public ResponseEntity<?> getRoles() {
        return ResponseEntity.ok(userService.getRoles());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createUser(@Valid @RequestBody UserCreateRequest request) {
        try {
            UserResponseDTO userResponseDTO = userService.createUser(request);
            return ResponseEntity.ok(userResponseDTO);
        } catch (CustomException ex) {
            return ResponseEntity.status(ex.getHttpStatus()).body(ex.getMessage());
        }
    }


    @DeleteMapping("/{username}")
    public ResponseEntity<?> deleteUser(@PathVariable String username) {
        this.userService.delete(username);
        return ResponseEntity.ok("Succesfully deleted");
    }


}
