package caugarde.vote.model.entity;


import caugarde.vote.model.entity.redis.CachedRanking;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name="RANKING")
@Getter
@NoArgsConstructor
@AllArgsConstructor
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

    @Column(name = "ATTENDANCE_COUNT")
    private int attendanceCount;

    public Ranking(CachedRanking cachedRanking){
        this.rankingPk = cachedRanking.getRankingPk();
        this.student = cachedRanking.getStudent();
        this.semester = cachedRanking.getSemester();
        this.attendanceCount = cachedRanking.getAttendanceCount();
    }

}
