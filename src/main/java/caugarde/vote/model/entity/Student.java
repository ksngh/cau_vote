package caugarde.vote.model.entity;

import caugarde.vote.common.util.RoleConverter;
import caugarde.vote.model.dto.student.StudentDetailsUpdate;
import caugarde.vote.model.dto.student.StudentUpdate;
import caugarde.vote.model.enums.MemberType;
import caugarde.vote.model.enums.Role;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.EnumSet;
import java.util.Set;

@Entity
@Table(name = "STUDENT")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "AUTHORITY", nullable = false)
    @Convert(converter = RoleConverter.class)
    private Set<Role> authorities;

    @Column(name = "UNIVERSITY_ID", length = 30)
    private String universityId;

    @Column(name = "EMAIL", nullable = false, length = 50)
    private String email;

    @Column(name = "NAME", length = 30)
    private String name;

    @Column(name = "MAJORITY", length = 50)
    private String majority;

    @Column(name = "MEMBER_TYPE", length = 10)
    @Enumerated(EnumType.STRING)
    private MemberType memberType;

    @Column(name = "OVERDUE_FINE", nullable = false)
    private Integer overdueFine;

    @Column(name = "CREATED_AT", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;

    @Column(name = "DELETED_AT")
    private LocalDateTime deletedAt;

    private Student(String email) {
        this.email = email;
        this.createdAt = LocalDateTime.now();
        this.overdueFine = 0;
        this.authorities = EnumSet.of(Role.PENDING_USER);
    }

    public static Student create(String email) {
        return new Student(email);
    }

    public void updateInitialInfo(StudentUpdate.Request request) {
        this.universityId = request.getUniversityId();
        this.name = request.getName();
        this.majority = request.getMajority();
        this.memberType = request.getMemberType();
        this.authorities = EnumSet.of(Role.USER);
        this.updatedAt = LocalDateTime.now();
    }

    public void updateDetailsInfo(StudentDetailsUpdate.Request request) {
        this.universityId = request.getUniversityId();
        this.name = request.getName();
        this.majority = request.getMajority();
        this.memberType = request.getMemberType();
        this.authorities = EnumSet.of(request.getRole());
        this.updatedAt = LocalDateTime.now();
    }

    public void delete() {
        this.deletedAt = LocalDateTime.now();
    }

    public void imposeOverDueFine(int overdueFine) {
        this.overdueFine = overdueFine;
    }

    public void paidOverDueFine() {
        this.overdueFine = 0;
    }
}
