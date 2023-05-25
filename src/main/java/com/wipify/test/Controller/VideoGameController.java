package com.wipify.test.Controller;

import com.wipify.test.model.VideoGame;
import com.wipify.test.repository.VideoGameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;

@RestController
public class VideoGameController {
    @Autowired
    private VideoGameRepository videoGameRepository;

    @GetMapping(value = "/articles")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin
    public Iterable<VideoGame> getVideoGames(){
        return videoGameRepository.findAll();
    }


    @PostMapping(value = "/article", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @CrossOrigin
    public VideoGame createVideoGame(@RequestBody VideoGame videoGame) {
        videoGame.setDate(new Date());
        return videoGameRepository.save(videoGame);
    }


    @GetMapping("/article/{id}")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin
    public ResponseEntity<VideoGame> getVideoGameById(@PathVariable int id) {
        Optional<VideoGame> optionalVideoGame = videoGameRepository.findById(id);
        if (optionalVideoGame.isPresent()) {
            VideoGame videoGame = optionalVideoGame.get();
            return ResponseEntity.ok(videoGame);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping(value = "/article/{id}", consumes = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin
    public ResponseEntity<VideoGame> updateArticle(@PathVariable int id, @RequestBody VideoGame updatedArticle){

        // Recherche de l'article existant avec l'ID spécifié dans le chemin de la requête
        Optional<VideoGame> optionalArticle = videoGameRepository.findById(id);
        if(optionalArticle.isPresent()){

            // Si l'article existe, on le récupère de l'Optional
            VideoGame article = optionalArticle.get();
            // Mise à jour des propriétés de l'article avec les valeurs de l'article mis à jour reçu dans le corps de la requête
            article.setContent(updatedArticle.getContent());
            article.setResume(updatedArticle.getResume());
            article.setTitle(updatedArticle.getTitle());
            article.setImageUrl(updatedArticle.getImageUrl());
            // Sauvegarde de l'article mis à jour dans le référentiel (base de données)
            VideoGame savedArticle = videoGameRepository.save(article);
            // Retourne une réponse avec le statut 200 OK et l'article mis à jour
            return ResponseEntity.ok(savedArticle);
        }else {
            // Si l'article avec l'ID spécifié n'est pas trouvé, retourne une réponse 404 Not Found
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/article/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CrossOrigin
    public ResponseEntity<Void> deleteVideoGame(@PathVariable int id) {
        // Recherche de l'article à supprimer
        Optional<VideoGame> optionalVideoGame = videoGameRepository.findById(id);
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
