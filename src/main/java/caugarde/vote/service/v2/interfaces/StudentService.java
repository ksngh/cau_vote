package caugarde.vote.service.v2.interfaces;

import caugarde.vote.model.entity.Student;

import java.util.List;

public interface StudentService {

    Student getByEmail(String email);

    List<Student> getAllStudents();
}
