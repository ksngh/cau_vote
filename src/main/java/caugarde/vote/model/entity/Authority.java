package caugarde.vote.model.entity;

import caugarde.vote.model.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "AUTHORITY")
@Getter
public class Authority {

    @Id
    @Column(name = "AUTHORITY_PK", nullable = false)
    private UUID authorityPk;

    @Enumerated(EnumType.STRING)
    @Column(name = "ROLE")
    private Role role;

    @OneToMany(mappedBy = "authority")
    private Set<Student> students;

    @OneToMany(mappedBy = "authority")
    private Set<Admin> admins;
}
