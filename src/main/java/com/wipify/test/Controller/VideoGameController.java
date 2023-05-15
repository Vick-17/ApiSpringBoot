package com.wipify.test.Controller;

import com.wipify.test.model.VideoGame;
import com.wipify.test.repository.VideoGameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class VideoGameController {
    @Autowired
    private VideoGameRepository videoGameRepository;

    @GetMapping(value = "/videogames")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin
    public Iterable<VideoGame> getVideoGames(){
        return videoGameRepository.findAll();
    }


    @PostMapping(value = "/videogames", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @CrossOrigin
    public VideoGame createVideoGame(@RequestBody VideoGame videoGame) {
        return videoGameRepository.save(videoGame);
    }
}
