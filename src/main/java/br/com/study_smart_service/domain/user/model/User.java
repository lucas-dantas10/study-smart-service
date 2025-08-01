package br.com.study_smart_service.domain.user.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class User {

    private UUID id;
    private String name;
    private String email;
    private String picture;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public User() {
    }

    public User(UUID id, String name, String email, String picture) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = null;
    }

    public User update(String name, String email, String picture) {
        this.name = name;
        this.email = email;
        this.picture = picture;

        return this;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.length() < 3) {
            throw new IllegalArgumentException("Nome deve ter pelo menos 3 caracteres.");
        }

        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
