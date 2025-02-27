package caugarde.vote.service.v2.interfaces;

import caugarde.vote.model.dto.student.StudentDetailsUpdate;
import caugarde.vote.model.dto.student.StudentInfo;
import caugarde.vote.model.dto.student.StudentUpdate;
import caugarde.vote.model.entity.Student;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface StudentService {

    Student getByEmail(String email);

    Student getById(Long id);

    List<Student> getNotPendingStudents();

    void update(String email, StudentUpdate.Request request);

    void adminUpdate(Long studentId, StudentDetailsUpdate.Request request);

    void paidLateFee(Long studentId);

    Slice<StudentInfo.Response> pageStudents(Long cursorId,int size);

    void deleteStudent(Long studentId);
}
