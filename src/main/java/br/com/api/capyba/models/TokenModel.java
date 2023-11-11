package br.com.api.capyba.models;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

@Entity
@Table(name = "tokens")
public class TokenModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer token;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserModel user;
    private LocalDateTime dateExpireToken;

    public TokenModel() {
    }

    public TokenModel(Integer token, UserModel user) {
        this.user = user;
        this.token = token;
        this.dateExpireToken = LocalDateTime.now().plusMinutes(30);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getToken() {
        return token;
    }

    public void setToken(Integer token) {
        this.token = token;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public LocalDateTime getDateExpireToken() {
        return dateExpireToken;
    }

    public void setDateExpireToken(LocalDateTime dateExpireToken) {
        this.dateExpireToken = dateExpireToken;
    }


}
