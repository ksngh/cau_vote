package caugarde.vote.model.entity;

import caugarde.vote.model.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;


import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "AUTHORITY")
@Getter
@NoArgsConstructor
public class Authority {

    @Id
    private UUID authorityPk;

    @Enumerated(EnumType.STRING)
    @Column(name = "ROLE")
    private Role role;

    public Authority(UUID authorityPk, Role role) {
        this.authorityPk = authorityPk;
        this.role = role;
    }

}
