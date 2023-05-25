package com.wipify.test.repository;

import com.wipify.test.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLoginRepository extends JpaRepository<User, Integer> {
    User findByEmail(String userEmail);
    User findByUsername(String username);
}
