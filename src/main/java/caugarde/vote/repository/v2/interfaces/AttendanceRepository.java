package caugarde.vote.repository.v2.interfaces;

import caugarde.vote.model.entity.Attendance;
import caugarde.vote.model.entity.Student;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface AttendanceRepository {

    void save(Attendance attendance);

    Map<Long, Integer> getStudentVoteCounts(List<Student> students);

    void saveAll(List<Attendance> attendances);

    List<Attendance> findTop10Attendance(String semester);

    Optional<Attendance> findByStudentAndSemester(Student student, String semester);

}
