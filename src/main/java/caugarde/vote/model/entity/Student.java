package caugarde.vote.model.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;


@Entity
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
