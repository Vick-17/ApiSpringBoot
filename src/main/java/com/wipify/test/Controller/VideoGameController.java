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


    @GetMapping("article/{id}")
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


}
