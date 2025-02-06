package caugarde.vote.model.entity;

import caugarde.vote.common.util.RoleConverter;
import caugarde.vote.model.enums.Role;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.EnumSet;
import java.util.HashSet;
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
    private String memberType;

    @Column(name = "CREATED_AT", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;

    @Column(name = "DELETED_AT")
    private LocalDateTime deletedAt;

    private Student(String email) {
        this.email = email;
        this.createdAt = LocalDateTime.now();
        this.authorities = EnumSet.of(Role.PENDING_USER);
    }

    public static Student create(String email){
        return new Student(email);
    }

    private Student(String universityId,String name, String majority, String memberType){
        this.universityId = universityId;
        this.name = name;
        this.majority = majority;
        this.memberType = memberType;
        this.authorities = EnumSet.of(Role.USER);
        this.updatedAt = LocalDateTime.now();
    }

    public static Student inputInfo(String universityId, String email, String name, String majority, String memberType){
        return new Student(universityId, email, name, majority);
    }

    public void addRole(Role role) {
        authorities.add(role);
        this.updatedAt = LocalDateTime.now();
    }

    public void removeRole(Role role) {
        authorities.remove(role);
        this.updatedAt = LocalDateTime.now();
    }

    public boolean hasRole(Role role) {
        return authorities.contains(role);
    }

    public void delete(){
        this.deletedAt = LocalDateTime.now();
    }

}
