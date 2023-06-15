package com.wipify.test.Controller;

import com.wipify.test.model.Article;
import com.wipify.test.repository.VideoGameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;

@RestController
public class ArticleController {
    @Autowired
    private VideoGameRepository videoGameRepository;

    @GetMapping(value = "/articles")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin
    public Iterable<Article> getVideoGames() {
        return videoGameRepository.findAll();
    }


    @PostMapping(value = "/articles", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @CrossOrigin
    public Article createVideoGame(@RequestBody Article article) {
        article.setDate(new Date());
        return videoGameRepository.save(article);
    }


    @GetMapping("/articles/{id}")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin
    public ResponseEntity<Article> getVideoGameById(@PathVariable int id) {
        Optional<Article> optionalVideoGame = videoGameRepository.findById(id);
        if (optionalVideoGame.isPresent()) {
            Article article = optionalVideoGame.get();
            return ResponseEntity.ok(article);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping(value = "/articles/{id}", consumes = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin
    public ResponseEntity<Article> updateArticle(@PathVariable int id, @RequestBody Article updatedArticle) {

        // Recherche de l'article existant avec l'ID spécifié dans le chemin de la requête
        Optional<Article> optionalArticle = videoGameRepository.findById(id);
        if (optionalArticle.isPresent()) {

            // Si l'article existe, on le récupère de l'Optional
            Article article = optionalArticle.get();
            // Mise à jour des propriétés de l'article avec les valeurs de l'article mis à jour reçu dans le corps de la requête
            article.setContent(updatedArticle.getContent());
            article.setResume(updatedArticle.getResume());
            article.setTitle(updatedArticle.getTitle());
            article.setImageUrl(updatedArticle.getImageUrl());
            // Sauvegarde de l'article mis à jour dans le référentiel (base de données)
            Article savedArticle = videoGameRepository.save(article);
            // Retourne une réponse avec le statut 200 OK et l'article mis à jour
            return ResponseEntity.ok(savedArticle);
        } else {
            // Si l'article avec l'ID spécifié n'est pas trouvé, retourne une réponse 404 Not Found
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/articles/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CrossOrigin
    public ResponseEntity<Void> deleteVideoGame(@PathVariable int id) {
        // Recherche de l'article à supprimer
        Optional<Article> optionalVideoGame = videoGameRepository.findById(id);
        if (optionalVideoGame.isPresent()) {
            // Si l'article existe, on le supprime
            videoGameRepository.delete(optionalVideoGame.get());
            // Retourne une réponse avec le statut 204 No Content pour indiquer que la suppression a été effectuée avec succès
            return ResponseEntity.noContent().build();
        } else {
            // Si l'article avec l'ID spécifié n'est pas trouvé, retourne une réponse 404 Not Found
            return ResponseEntity.notFound().build();
        }
    }
}
