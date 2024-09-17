package caugarde.vote.model.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "STUDENT_VOTE")
@Getter
@NoArgsConstructor
public class StudentVote {

    @Id
    @Column(name = "STUDENT_VOTE_PK", nullable = false)
    private UUID studentVotePk;

    @ManyToOne
    @JoinColumn(name = "STUDENT_FK", nullable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "VOTE_FK", nullable = false)
    private Vote vote;

    @Builder
    public StudentVote(UUID studentVotePk, Student student, Vote vote) {
        this.studentVotePk = studentVotePk;
        this.student = student;
        this.vote = vote;
    }
}
