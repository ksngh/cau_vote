package caugarde.vote.model.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Set;

@Entity
@Table(name = "VOTE")
@NoArgsConstructor
public class Vote {

    @Id
    @Column(name = "VOTE_PK", nullable = false)
    private String votePk;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "CONTENT")
    private String content;

    @Column(name = "START_DATE")
    private java.util.Date startDate;

    @Column(name = "SUBMIT_DATE")
    private java.util.Date submitDate;

    @Column(name = "LIMIT_PEOPLE")
    private Integer limitPeople;

    @OneToMany(mappedBy = "vote")
    private Set<StudentVote> studentVotes;

    @Builder
    public Vote(String votePk, String title, String content, Timestamp startDate, Timestamp submitDate, int limitPeople) {
        this.votePk = votePk;
        this.title = title;
        this.content = content;
        this.startDate = startDate;
        this.submitDate = submitDate;
        this.limitPeople = limitPeople;
    }


    // Getters and Setters
}