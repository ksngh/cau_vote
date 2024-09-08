package caugarde.vote.model.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;


@Entity
@Setter
@Getter
public class Student {

    @Id
    private String studentPk;
    private int studentId;
    private String majority;
    private String name;
    private String role;
    private String member;

    // Getters and Setters
}
