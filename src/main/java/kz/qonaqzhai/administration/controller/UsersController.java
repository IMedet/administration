package kz.qonaqzhai.administration.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kz.qonaqzhai.administration.service.IUserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UsersController {

    private final IUserService userService;

    @GetMapping
    public ResponseEntity<?> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id){
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUserByIdNew(@PathVariable Long id){
        return ResponseEntity.ok(userService.getUserByIdNew(id));
    }

    @GetMapping("/user/iin/{iin}")
    public ResponseEntity<?> getUserByIinNew(@PathVariable String iin){
        return ResponseEntity.ok(userService.getUserByIinNew(iin));
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<?> getUserByUsername(@PathVariable String username){
        return ResponseEntity.ok(userService.getUserByUsername(username));
    }
}
