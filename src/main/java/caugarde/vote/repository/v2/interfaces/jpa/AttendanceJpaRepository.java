package caugarde.vote.repository.v2.interfaces.jpa;

import caugarde.vote.model.entity.Attendance;
import caugarde.vote.model.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AttendanceJpaRepository extends JpaRepository<Attendance, Long> {

    Optional<Attendance> findByStudentAndSemester(Student student, String semester);

}
