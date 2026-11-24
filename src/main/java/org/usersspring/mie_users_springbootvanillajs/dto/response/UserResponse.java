package org.usersspring.mie_users_springbootvanillajs.dto.response;

public record UserResponse(
        Long userId,
        String name,
        String email
) {
}
