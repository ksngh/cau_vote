package caugarde.vote.model.entity;

import caugarde.vote.model.enums.Role;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "STUDENT")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Column(name = "AUTHORITY", nullable = false)
    private Set<Role> authorities;

    @Column(name = "STUDENT_ID", nullable = false, length = 30)
    private String studentId;

    @Column(name = "EMAIL", nullable = false, length = 50)
    private String email;

    @Column(name = "NAME", nullable = false, length = 30)
    private String name;

    @Column(name = "MAJORITY", nullable = false, length = 50)
    private String majority;

    @Column(name = "MEMBER_TYPE", nullable = false, length = 10)
    private String memberType;

    @Column(name = "CREATED_AT", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;

    @Column(name = "DELETED_AT")
    private LocalDateTime deletedAt;
}
