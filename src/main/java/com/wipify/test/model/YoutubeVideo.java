package com.wipify.test.model;

import jakarta.persistence.*;

@Entity
@Table(name="youtube_video")
public class YoutubeVideo {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private int id;
private String url;

    public YoutubeVideo() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
