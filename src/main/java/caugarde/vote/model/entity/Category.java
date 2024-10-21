package caugarde.vote.model.entity;

import caugarde.vote.model.enums.Type;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "CATEGORY")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    @Id
    @Column(name = "CATEGORY_PK", nullable = false, unique = true)
    private UUID categoryPk;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private Type type;

}
