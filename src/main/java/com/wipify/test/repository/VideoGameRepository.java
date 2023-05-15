package com.wipify.test.repository;

import com.wipify.test.model.VideoGame;
import org.springframework.data.repository.CrudRepository;

public interface VideoGameRepository extends CrudRepository<VideoGame, Integer> {

}
