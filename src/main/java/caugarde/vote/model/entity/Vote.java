package caugarde.vote.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "VOTE")
@NoArgsConstructor
@Getter
@ToString
public class Vote {

    @Id
    @Column(name = "VOTE_PK", nullable = false)
    private UUID votePk;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "CONTENT", length = 1000)
    private String content;

    @Column(name = "START_DATE")
    private Timestamp startDate;

    @Column(name = "SUBMIT_DATE")
    private Timestamp submitDate;

    @Column(name = "LIMIT_PEOPLE")
    private int limitPeople;

    @OneToMany(mappedBy = "vote")
    private Set<StudentVote> studentVotes;

    @Builder
    public Vote(UUID votePk, String title, String content, Timestamp startDate, Timestamp submitDate, int limitPeople) {
        this.votePk = votePk;
        this.title = title;
        this.content = content;
        this.startDate = startDate;
        this.submitDate = submitDate;
        this.limitPeople = limitPeople;
    }


    // Getters and Setters
}