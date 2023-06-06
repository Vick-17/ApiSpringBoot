package com.wipify.test.repository;

import com.wipify.test.model.Article;
import org.springframework.data.repository.CrudRepository;

public interface VideoGameRepository extends CrudRepository<Article, Integer> {

}
