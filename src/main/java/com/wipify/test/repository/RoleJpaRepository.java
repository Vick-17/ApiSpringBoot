package com.wipify.test.repository;

import com.wipify.test.model.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleJpaRepository extends JpaRepository<RoleEntity, Integer> {
    RoleEntity findByName(String name);
}
