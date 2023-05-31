package com.wipify.test.repository;

import com.wipify.test.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLoginRepository extends JpaRepository<UserEntity, Integer> {
    UserEntity findByEmail(String userEmail);
    UserEntity findByPseudo(String pseudo);
}
