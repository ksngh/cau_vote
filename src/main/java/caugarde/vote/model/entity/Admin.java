package caugarde.vote.model.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.UUID;

@Entity
@Table(name = "ADMIN")
@Getter
public class Admin {

    @Id
    @Column(name = "ADMIN_PK", nullable = false)
    private UUID adminPk;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "AUTHORITY_FK", nullable = false)
    private Authority authority;

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "PASSWORD")
    private String password;

    // Getters and Setters
}