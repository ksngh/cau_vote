package caugarde.vote.model.entity;

import caugarde.vote.model.dto.board.BoardCreate;
import caugarde.vote.model.dto.board.BoardUpdate;
import caugarde.vote.model.enums.BoardStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "Board")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

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

    private Board(BoardCreate.Request request,Student student) {
        this.title = request.getTitle();
        this.createdBy = student;
        this.content = request.getContent();
        this.limitPeople = request.getLimitPeople();
        this.startDate = request.getStartDate();
        this.endDate = request.getEndDate();
        this.createdAt = LocalDateTime.now();
        this.status = BoardStatus.PENDING;
    }

    public static Board create(BoardCreate.Request request,Student student) {
        return new Board(request,student);
    }

    public void update(BoardUpdate.Request request,String email){
        this.title= request.getTitle();
        this.content= request.getContent();
        this.limitPeople = request.getLimitPeople();
        this.startDate = request.getStartDate();
        this.endDate = request.getEndDate();
        this.updatedAt = LocalDateTime.now();
        this.updatedBy = email;
    }

    public void onSoftDelete(String email) {
        this.deletedAt = LocalDateTime.now();
        this.deletedBy = email;
    }

}

