package com.wipify.test.model;

import jakarta.persistence.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

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
    private Boolean isVerified;

    private String confimartionLink;
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

    public String getConfimartionLink() {
        return confimartionLink;
    }

    public void setConfimartionLink(String confimartionLink) {
        this.confimartionLink = confimartionLink;
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

}