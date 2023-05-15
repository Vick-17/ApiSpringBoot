package com.wipify.test.model;
import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name="article")
public class VideoGame {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String titre;
    @Column(name="date_publication")
    private Date publiDate;
    private String contenu;
    private String resume;
    @Column(name="image_url")
    private String imageUrl;

    public VideoGame(int id, String titre, Date publiDate, String contenu, String resume, String imageUrl) {
        this.id = id;
        this.titre = titre;
        this.publiDate = publiDate;
        this.contenu = contenu;
        this.resume = resume;
        this.imageUrl = imageUrl;
    }

    public VideoGame() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public Date getPubliDate() {
        return publiDate;
    }

    public void setPubliDate(Date publiDate) {
        this.publiDate = publiDate;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
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
