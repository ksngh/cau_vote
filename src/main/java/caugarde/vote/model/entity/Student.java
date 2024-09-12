package caugarde.vote.model.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Entity
@Getter
@NoArgsConstructor
public class Student {

    @Id
    private UUID studentPk;
    private String studentId;
    private String email;
    private String majority;
    private String name;
    private String role;
    private String memberType;

    public Student(UUID studentPk, String email) {
        this.studentPk = studentPk;
        this.email = email;
    }

    @Builder
    public Student(UUID studentPk, String studentId, String email, String majority, String name, String role, String memberType) {
        this.studentPk = studentPk;
        this.studentId = studentId;
        this.email = email;
        this.majority = majority;
        this.name = name;
        this.role = role;
        this.memberType = memberType;
    }
}
