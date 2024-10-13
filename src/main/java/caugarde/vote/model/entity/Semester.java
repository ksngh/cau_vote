package caugarde.vote.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "Semester")
@Getter
@NoArgsConstructor
public class Semester {

    @Id
    @Column(name = "SEMESTER_PK", columnDefinition = "UUID")
    private UUID semesterPk;

    @Column(name = "SEMESTER", unique = true)
    private String semester;

    @OneToMany(mappedBy = "semester")
    private Set<Ranking> rankings;

    public Semester(UUID semesterPk, String semester) {
        this.semesterPk = semesterPk;
        this.semester = semester;
    }
}

