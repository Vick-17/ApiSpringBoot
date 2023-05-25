package com.wipify.test.repository;

import com.wipify.test.model.RoleEntity;

public interface RoleJpaRepository {
    RoleEntity findByName(String name);
}
