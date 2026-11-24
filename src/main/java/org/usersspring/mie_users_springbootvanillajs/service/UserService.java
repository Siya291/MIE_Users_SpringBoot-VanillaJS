package org.usersspring.mie_users_springbootvanillajs.service;

import org.apache.catalina.User;
import org.usersspring.mie_users_springbootvanillajs.dto.request.UserRequest;
import org.usersspring.mie_users_springbootvanillajs.dto.response.UserResponse;

import java.util.List;

public interface UserService {

    UserResponse createUser(UserRequest request);

    List<UserResponse> findAll();

    UserResponse findById(Long userId);

    UserResponse updateUser(Long userId, UserRequest request);

    void deleteUser(Long userId);
}
