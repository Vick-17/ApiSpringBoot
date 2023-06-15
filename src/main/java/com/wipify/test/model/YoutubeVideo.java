package com.wipify.test.model;

import jakarta.persistence.*;

import java.util.Date;


@Entity
@Table(name="youtube_video")
public class YoutubeVideo {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private int id;
private String url;

private String name;

private Date date;

    public YoutubeVideo() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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
