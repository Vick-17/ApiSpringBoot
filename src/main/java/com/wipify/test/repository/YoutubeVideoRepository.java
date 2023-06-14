package com.wipify.test.repository;

import com.wipify.test.model.YoutubeVideo;
import org.springframework.data.repository.CrudRepository;

public interface YoutubeVideoRepository extends CrudRepository<YoutubeVideo, Integer> {
}
