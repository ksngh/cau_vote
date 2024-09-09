package caugarde.vote.model.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;


@Entity
@Getter
public class Student {

    @Id
    private UUID studentPk;
    private int studentId;
    private String email;
    private String majority;
    private String name;
    private String role;
    private String memberType;

    public Student(UUID studentPk,String email, String name, String role) {
        this.studentPk = studentPk;
        this.name = name;
        this.role = role;
        this.email = email;
    }

    public void setStudentInfo(int studentId, String majority, String name,String memberType) {
        this.studentId = studentId;
        this.majority = majority;
        this.name = name;
        this.memberType = memberType;
    }

}
