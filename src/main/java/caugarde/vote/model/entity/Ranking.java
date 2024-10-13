package caugarde.vote.model.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name="RANKING")
@Getter
@NoArgsConstructor
public class Ranking {

    @Id
    @Column(name = "RANKING_PK", nullable = false)
    private UUID rankingPk;

    @ManyToOne
    @JoinColumn(name = "STUDENT_FK", nullable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "SEMESTER", nullable = false)
    private Semester semester;

    @Column(name = "RANK")
    private Integer rank;

    @Column(name = "ATTENDANCE_COUNT")
    private Integer attendanceCount;

}
