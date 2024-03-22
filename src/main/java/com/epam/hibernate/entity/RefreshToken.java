package com.epam.hibernate.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;

import java.time.Instant;

@Entity(name = "refreshtoken")
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne(orphanRemoval = true, fetch = FetchType.EAGER)
    @Cascade({org.hibernate.annotations.CascadeType.ALL,
            org.hibernate.annotations.CascadeType.DELETE_ORPHAN})
    private User user;
    @Column(nullable = false, unique = true)
    private String token;
    @Column(nullable = false)
    private Instant expiryDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Instant getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Instant expiryDate) {
        this.expiryDate = expiryDate;
    }
}

