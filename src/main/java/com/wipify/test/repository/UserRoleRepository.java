package com.wipify.test.repository;


import com.wipify.test.model.UserRoleEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRoleRepository extends CrudRepository<UserRoleEntity, Integer> {

}
