package com.wipify.test.Controller;

import com.wipify.test.model.YoutubeVideo;
import com.wipify.test.repository.YoutubeVideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
public class YoutubeVideoController {
    @Autowired
    private YoutubeVideoRepository youtubeVideoRepository;

    @PostMapping(value = "/youtubevideo", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @CrossOrigin
    public YoutubeVideo addYoutubeVideo(@RequestBody YoutubeVideo youtubeVideo) {
        youtubeVideo.setDate(new Date());
        return youtubeVideoRepository.save(youtubeVideo);
    }

    @GetMapping(value = "/youtubeVideo")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin
    public Iterable<YoutubeVideo> getYoutubeVideoUrl(){
        return youtubeVideoRepository.findAll();
    }
}
