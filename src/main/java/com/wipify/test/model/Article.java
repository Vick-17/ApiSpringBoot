package com.wipify.test.model;
import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name="article")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private Date date;
    private String content;
    private String resume;
    @Column(name="image_url")
    private String imageUrl;

    public Article(int id, String title, Date date, String content, String resume, String imageUrl) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.content = content;
        this.resume = resume;
        this.imageUrl = imageUrl;
    }

    public Article() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
