package caugarde.vote.model.entity;

import caugarde.vote.model.enums.Role;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "AUTHORITY")
public class Authority {

    @Id
    @Column(name = "AUTHORITY_PK", nullable = false)
    private String authorityPk;

    @Enumerated(EnumType.STRING)
    @Column(name = "ROLE")
    private Role role;

    @OneToMany(mappedBy = "authority")
    private Set<Student> students;

    @OneToMany(mappedBy = "authority")
    private Set<Admin> admins;
}
