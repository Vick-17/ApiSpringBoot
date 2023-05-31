package com.wipify.test.repository;

import com.wipify.test.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<UserEntity, Integer> {
    UserEntity findByPseudo(String username);
}
