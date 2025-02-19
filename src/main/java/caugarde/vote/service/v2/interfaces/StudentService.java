package caugarde.vote.service.v2.interfaces;

import caugarde.vote.model.dto.student.StudentUpdate;
import caugarde.vote.model.entity.Student;

import java.util.List;

public interface StudentService {

    Student getByEmail(String email);

    Student getById(Long id);

    List<Student> getAllStudents();

    void update(Long studentId, StudentUpdate.Request request);
}
