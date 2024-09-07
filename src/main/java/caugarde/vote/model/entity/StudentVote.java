package caugarde.vote.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class StudentVote {
    @Id
    private String studentVotePk;

    @ManyToOne
    @JoinColumn(name = "vote_fk")
    private Vote vote;

    @ManyToOne
    @JoinColumn(name = "student_fk")
    private Student student;

    private int choice;

    // Getters and Setters
}
