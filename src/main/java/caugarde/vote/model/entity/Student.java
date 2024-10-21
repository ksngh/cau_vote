package caugarde.vote.model.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;


@Entity
@Table(name = "STUDENT")
@NoArgsConstructor
@Getter
public class Student {

    @Id
    @Column(name = "STUDENT_PK", nullable = false)
    private UUID studentPk;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "AUTHORITY_FK", nullable = false)
    private Authority authority;

    @Column(name = "STUDENT_ID")
    private String studentId;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "NAME")
    private String name;

    @Column(name = "MAJORITY")
    private String majority;

    @Column(name = "MEMBER_TYPE")
    private String memberType;

    @CreationTimestamp
    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "student",cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<StudentVote> studentVotes;

    @OneToMany(mappedBy = "student",cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Ranking> rankings;

    public Student(UUID studentPk, String email) {
        this.studentPk = studentPk;
        this.email = email;
    }

    @Builder
    public Student(UUID studentPk, String studentId, String email, String majority, String name, Authority authority, String memberType) {
        this.studentPk = studentPk;
        this.studentId = studentId;
        this.email = email;
        this.majority = majority;
        this.name = name;
        this.authority = authority;
        this.memberType = memberType;
    }

}
