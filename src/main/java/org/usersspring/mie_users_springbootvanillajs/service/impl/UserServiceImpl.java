package org.usersspring.mie_users_springbootvanillajs.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.usersspring.mie_users_springbootvanillajs.dto.request.UserRequest;
import org.usersspring.mie_users_springbootvanillajs.dto.response.UserResponse;
import org.usersspring.mie_users_springbootvanillajs.entity.UserEntity;
import org.usersspring.mie_users_springbootvanillajs.exceptions.DuplicateResourceException;
import org.usersspring.mie_users_springbootvanillajs.exceptions.ResourceNotFoundException;
import org.usersspring.mie_users_springbootvanillajs.repository.UserRepository;
import org.usersspring.mie_users_springbootvanillajs.service.UserService;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserResponse createUser(UserRequest request) {

        if (userRepository.existsByEmail(request.email())) {
            throw new DuplicateResourceException("A user with email: " + request.email() + " already exists");
        }

        UserEntity user = new UserEntity();
        user.setName(request.name());
        user.setEmail(request.email());

        UserEntity savedUser = userRepository.save(user);

        return mapToResponse(savedUser);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponse> findAll() {
        return userRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse findById(Long userId) {
        UserEntity user = getUserByID(userId);

        return mapToResponse(user);
    }

    @Override
    public UserResponse updateUser(Long userId, UserRequest request) {
        UserEntity existingUser = getUserByID(userId);

        if (!existingUser.getEmail().equals(request.email()) && userRepository.existsByEmail(request.email())) {
            throw new DuplicateResourceException("A user with email: " + request.email() + " already exists");
        }

        existingUser.setName(request.name());
        existingUser.setEmail(request.email());

        UserEntity updatedUser = userRepository.save(existingUser);

        return mapToResponse(updatedUser);
    }

    @Override
    public void deleteUser(Long userId) {
        UserEntity existingUser = getUserByID(userId);

        userRepository.delete(existingUser);
    }

    private UserEntity getUserByID(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));
    }

    private UserResponse mapToResponse(UserEntity user) {
        return new UserResponse(
                user.getUserId(),
                user.getName(),
                user.getEmail()
        );
    }
}