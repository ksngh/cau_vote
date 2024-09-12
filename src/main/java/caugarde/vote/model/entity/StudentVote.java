package caugarde.vote.model.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.UUID;

@Entity
@Table(name = "STUDENT_VOTE")
@Getter
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

    @Column(name = "CHOICE")
    private int choice;


}
