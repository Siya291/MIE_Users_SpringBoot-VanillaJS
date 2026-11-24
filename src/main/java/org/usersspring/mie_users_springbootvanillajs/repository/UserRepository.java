package org.usersspring.mie_users_springbootvanillajs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.usersspring.mie_users_springbootvanillajs.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    boolean existsByEmail(String email);
}
