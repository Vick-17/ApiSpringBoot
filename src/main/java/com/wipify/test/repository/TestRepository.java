package com.wipify.test.repository;

import com.wipify.test.model.TestEntity;
import org.springframework.data.repository.CrudRepository;

public interface TestRepository extends CrudRepository<TestEntity, Integer> {
}
