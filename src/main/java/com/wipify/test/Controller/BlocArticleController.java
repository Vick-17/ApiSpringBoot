package com.wipify.test.Controller;

import com.wipify.test.model.BlocArticle;
import com.wipify.test.repository.BlocArticleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public class BlocArticleController {
    private BlocArticleRepository blocArticleRepository;
    @PostMapping(value = "/champs-article", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @CrossOrigin
    public BlocArticle createChampsArticle(@RequestBody BlocArticle champsArticle) {
        return blocArticleRepository.save(champsArticle);
    }
}
