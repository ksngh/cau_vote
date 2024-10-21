package caugarde.vote.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "VOTE")
@NoArgsConstructor
@Getter
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

    @CreationTimestamp
    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "vote",fetch = FetchType.LAZY)
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

}