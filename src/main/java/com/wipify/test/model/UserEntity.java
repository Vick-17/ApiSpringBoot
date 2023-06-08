package com.wipify.test.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import static jakarta.persistence.FetchType.EAGER;


@Entity
@Table(name = "users")
public class UserEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true)
    private String nom;
    private String prenom;
    private String pseudo;
    private String email;
    private String password;
    private String telephone;
    @Column(name = "photo_path")
    private String imageName;

    @JsonIgnore
    @Transient
    private MultipartFile imageFile;
    @Column(name = "is_verified")
    private Boolean isVerified;

    @Column(name = "confirmation_token")
    private String confirmationToken;
    @ManyToMany(fetch = EAGER)
    private Collection<RoleEntity> role = new ArrayList<>();

    public UserEntity(int id, String nom, String prenom, String pseudo, String email, String password, String telephone, Collection<RoleEntity> role) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.pseudo = pseudo;
        this.email = email;
        this.password = password;
        this.telephone = telephone;
        this.role = role;
    }

    public UserEntity() {
    }

    public UserEntity(String pseudo, String password, Collection<SimpleGrantedAuthority> authorities) {
        this.pseudo = pseudo;
        this.password = password;
    }

    public String getConfirmationToken() {
        return confirmationToken;
    }

    public void setConfirmationToken(String confirmationToken) {
        this.confirmationToken = confirmationToken;
    }

    public Boolean getVerified() {
        return isVerified;
    }

    public void setVerified(Boolean verified) {
        isVerified = verified;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public Collection<RoleEntity> getRole() {
        return role;
    }

    public void setRole(Collection<RoleEntity> role) {
        this.role = role;
    }

    public MultipartFile getImageFile() {
        return imageFile;
    }

    public void setImageFile(MultipartFile imageFile) {
        this.imageFile = imageFile;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
}
