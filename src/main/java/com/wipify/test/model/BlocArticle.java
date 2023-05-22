package com.wipify.test.model;

import jakarta.persistence.*;

public class BlocArticle {

    @Entity
    @Table(name = "champs_article")
    public class ChampsArticle {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "paragraphe", columnDefinition = "TEXT")
        private String paragraphe;

        @Column(name = "url_photo")
        private String urlPhoto;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "article_id")
        private VideoGame article;

        // Constructeurs, getters et setters

        public ChampsArticle() {
        }

        public ChampsArticle(String paragraphe, String urlPhoto, VideoGame article) {
            this.paragraphe = paragraphe;
            this.urlPhoto = urlPhoto;
            this.article = article;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getParagraphe() {
            return paragraphe;
        }

        public void setParagraphe(String paragraphe) {
            this.paragraphe = paragraphe;
        }

        public String getUrlPhoto() {
            return urlPhoto;
        }

        public void setUrlPhoto(String urlPhoto) {
            this.urlPhoto = urlPhoto;
        }

        public VideoGame getArticle() {
            return article;
        }

        public void setArticle(VideoGame article) {
            this.article = article;
        }
    }

}
