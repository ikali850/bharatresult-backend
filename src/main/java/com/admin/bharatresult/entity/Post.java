package com.admin.bharatresult.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String shortTitle;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String fullTitle;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false, unique = true)
    private String postUrl;

    @Column(nullable = true)
    private String updateMsg;

    @Column(nullable = false, unique = false, columnDefinition = "TEXT")
    private String category;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // Foreign key user_id
    private User user;

    private boolean isPublish = false;
    private long likes, views, shares;
    @Column(name = "created_date", updatable = false)
    private String createdDate;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    
    @PreUpdate
    public void preUpdate() {
        this.updatedDate = LocalDateTime.now();
    }
}
