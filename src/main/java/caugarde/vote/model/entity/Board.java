package caugarde.vote.model.entity;

import caugarde.vote.model.enums.BoardStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "Board")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Column(name = "TITLE", nullable = false, length = 50)
    private String title;

    @Column(name = "CONTENT", nullable = false, length = 1000)
    private String content;

    @Column(name = "STATUS", nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private BoardStatus status;

    @Column(name = "LIMIT_PEOPLE", nullable = false)
    private Integer limitPeople;

    @Column(name = "START_DATE", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "END_DATE", nullable = false)
    private LocalDateTime endDate;

    @Column(name = "CREATED_AT", nullable = false)
    private LocalDateTime createdAt;

    @JoinColumn(name = "CREATED_BY", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Student createdBy;

    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;

    @Column(name = "UPDATED_BY", length = 50)
    private String updatedBy;

    @Column(name = "DELETED_AT")
    private LocalDateTime deletedAt;

    @Column(name = "DELETED_BY")
    private String deletedBy;

    private Board(String title, String content, Integer limitPeople, LocalDateTime startDate, LocalDateTime endDate) {
        this.title = title;
        this.content = content;
        this.limitPeople = limitPeople;
        this.startDate = startDate;
        this.endDate = endDate;
        this.createdAt = LocalDateTime.now();
        this.status = BoardStatus.PENDING;
    }

    public static Board create(String title, String content, Integer limitPeople, LocalDateTime startDate, LocalDateTime endDate) {
        return new Board(title, content, limitPeople, startDate, endDate);
    }

    public void update() {
        this.updatedAt = LocalDateTime.now();
    }

    public void softDelete() {
        this.deletedAt = LocalDateTime.now();
    }
}

