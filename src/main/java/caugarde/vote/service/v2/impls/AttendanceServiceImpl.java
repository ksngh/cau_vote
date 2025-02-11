package caugarde.vote.service.v2.impls;

import caugarde.vote.model.entity.Attendance;
import caugarde.vote.model.entity.Student;
import caugarde.vote.model.entity.cached.StudentAttendanceCount;
import caugarde.vote.repository.v2.interfaces.AttendanceRepository;
import caugarde.vote.service.v2.interfaces.AttendanceService;
import caugarde.vote.service.v2.interfaces.StudentService;
import caugarde.vote.service.v2.interfaces.cached.StudentAttendanceCountService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final StudentAttendanceCountService studentAttendanceCountService;
    private final StudentService studentService;

    @Override
    @Transactional
    @Scheduled(cron = "0 0 0 * * *")
    public void createAttendance() {
        List<Student> students = studentService.getAllStudents();
        Map<Long, Integer> voteCounts = attendanceRepository.getStudentVoteCounts(students);
        List<Attendance> attendanceList = students.stream()
                .map(student -> Attendance.of(
                        student,
                        voteCounts.getOrDefault(student.getId(), 0)))
                .toList();
        attendanceRepository.saveAll(attendanceList);
        studentAttendanceCountService.saveStudentAttendanceCount(attendanceList.stream().map(StudentAttendanceCount::from).toList());
    }

    @Override
    public List<Attendance> getTop10Attendances(String semester) {
        return attendanceRepository.findTop10Attendance(semester);
    }

}
