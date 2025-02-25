package caugarde.vote.repository.v2.interfaces;

import caugarde.vote.model.dto.student.StudentInfo;
import caugarde.vote.model.entity.Student;
import org.springframework.data.domain.Slice;

import java.util.List;
import java.util.Optional;

public interface StudentRepository {

    void save (Student student);

    List<Student> findAll();

    Optional<Student> findByEmail(String email);

    Optional<Student> findById(Long id);

    Slice<StudentInfo.Response> pageStudents(Long cursorId,int size);

}
