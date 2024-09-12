package caugarde.vote.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "ADMIN")
public class Admin {

    @Id
    @Column(name = "ADMIN_PK", nullable = false)
    private String adminPk;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AUTHORITY_FK", nullable = false)
    private Authority authority;

    @Column(name = "ID")
    private String id;

    @Column(name = "PASSWORD")
    private String password;

    // Getters and Setters
}