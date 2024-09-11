package caugarde.vote.model.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Entity
@Getter
@NoArgsConstructor
public class Student {

    @Id
    private UUID studentPk;
    private int studentId;
    private String email;
    private String majority;
    private String name;
    private String role;
    private String memberType;

    public Student(UUID studentPk,String email) {
        this.studentPk = studentPk;
        this.email = email;
    }

    public void setStudentInfo(int studentId, String majority, String name,String memberType,String role) {
        this.studentId = studentId;
        this.majority = majority;
        this.name = name;
        this.memberType = memberType;
        this.role = role;
    }

}
