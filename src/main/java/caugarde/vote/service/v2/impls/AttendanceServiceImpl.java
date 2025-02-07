package caugarde.vote.service.v2.impls;

import caugarde.vote.model.dto.attendance.AttendanceInfo;
import caugarde.vote.model.entity.Attendance;
import caugarde.vote.model.entity.Student;
import caugarde.vote.model.entity.cached.MostActiveParticipant;
import caugarde.vote.repository.v2.interfaces.AttendanceRepository;
import caugarde.vote.repository.v2.interfaces.cached.MostActiveParticipantRepository;
import caugarde.vote.service.v2.interfaces.AttendanceService;
import caugarde.vote.service.v2.interfaces.StudentService;
import caugarde.vote.service.v2.interfaces.VoteService;
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
    private final StudentService studentService;
    private final MostActiveParticipantRepository mostActiveParticipantRepository;

    @Override
    @Transactional(readOnly = true)
    public List<AttendanceInfo.Response> getTopParticipants() {
        List<MostActiveParticipant> mostActiveParticipants = mostActiveParticipantRepository.findMostActiveParticipants();
        return mostActiveParticipants.stream()
                .map(AttendanceInfo.Response::from)
                .toList();
    }

    @Override
    public List<AttendanceInfo.Response> getTopTenParticipants() {
        return List.of();
    }

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
    }

}
