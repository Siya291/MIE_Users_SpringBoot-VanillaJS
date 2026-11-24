package org.usersspring.mie_users_springbootvanillajs.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.usersspring.mie_users_springbootvanillajs.dto.request.UserRequest;
import org.usersspring.mie_users_springbootvanillajs.dto.response.UserResponse;
import org.usersspring.mie_users_springbootvanillajs.service.UserService;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
public class UserController {

    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest request) {
        UserResponse savedUser = userService.createUser(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> findAllUsers() {
        List<UserResponse> existingUsers = userService.findAll();

        return ResponseEntity.ok(existingUsers);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> findById(@PathVariable Long userId) {
        UserResponse existingUser = userService.findById(userId);

        return ResponseEntity.ok(existingUser);
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long userId, @RequestBody UserRequest request) {
        UserResponse existingUser = userService.updateUser(userId, request);

        return ResponseEntity.ok(existingUser);
    }

    @DeleteMapping("/{userId}")
    public String deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);

        return "User with ID: " + userId + " deleted successfully";
    }

}
