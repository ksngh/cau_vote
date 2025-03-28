package caugarde.vote.model.entity;

import caugarde.vote.model.enums.FencingType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @JoinColumn(name = "STUDENT", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Student student;

    @JoinColumn(name = "BOARD", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;

    @Column(name = "TYPE", nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private FencingType fencingType;

    @Column(name = "CREATED_AT", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "DELETED_AT")
    private LocalDateTime deletedAt;

    private Vote(Student student, Board board, FencingType fencingType) {
        this.student = student;
        this.board = board;
        this.fencingType = fencingType;
        this.createdAt = LocalDateTime.now();
    }

    public static Vote of(Student student, Board board, FencingType fencingType) {
        return new Vote(student, board, fencingType);
    }

    public void softDelete(){
        this.deletedAt = LocalDateTime.now();
    }

}
