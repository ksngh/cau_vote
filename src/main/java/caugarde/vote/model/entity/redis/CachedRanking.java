package caugarde.vote.model.entity.redis;

import caugarde.vote.model.entity.Semester;
import caugarde.vote.model.entity.Student;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.springframework.data.redis.core.RedisHash;

import java.util.UUID;

@RedisHash("CachedRanking")
@Getter
@Builder
@AllArgsConstructor
public class CachedRanking{

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

