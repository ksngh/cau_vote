package caugarde.vote.service.v2.impls;

import caugarde.vote.common.util.SemesterUtil;
import caugarde.vote.model.dto.attendance.AttendanceInfo;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        List<Student> students = studentService.getNotPendingStudents();
        Map<Long, Integer> voteCounts = attendanceRepository.getStudentVoteCounts(students);

        Map<Long, Attendance> attendanceMap = students.stream()
                .map(student -> attendanceRepository.findByStudentAndSemester(
                                student, SemesterUtil.getSemester(LocalDate.now()))
                        .orElse(Attendance.of(student, 0)))
                .collect(Collectors.toMap(attendance -> attendance.getStudent().getId(), attendance -> attendance));

        attendanceMap.forEach((studentId, attendance) ->
                attendance.updateCount(voteCounts.getOrDefault(studentId, 0)));

        List<Attendance> updatedAttendances = new ArrayList<>(attendanceMap.values());
        attendanceRepository.saveAll(updatedAttendances);

        List<Attendance> top10Attendances = getTop10s(updatedAttendances);
        studentAttendanceCountService.deleteAllCache();
        studentAttendanceCountService.saveStudentAttendanceCount(top10Attendances.stream().map(StudentAttendanceCount::from).toList());
    }

    public List<Attendance> getTop10s(List<Attendance> attendanceList) {
        List<Attendance> sortedList = attendanceList.stream()
                .sorted(Comparator.comparingInt(Attendance::getAttendanceCount).reversed()) // 내림차순 정렬
                .toList();

        return sortedList.stream()
                .limit(getTop10CutoffIndex(sortedList))
                .toList();
    }

    private int getTop10CutoffIndex(List<Attendance> sortedList) {
        if (sortedList.size() <= 10) {
            return sortedList.size();
        }

        int cutoffIndex = 10;
        int cutoffValue = sortedList.get(9).getAttendanceCount();

        while (cutoffIndex < sortedList.size() && sortedList.get(cutoffIndex).getAttendanceCount() == cutoffValue) {
            cutoffIndex++;
        }

        return cutoffIndex;
    }

    @Override
    public List<Attendance> getTop10Attendances(String semester) {
        return attendanceRepository.findTop10Attendance(semester);
    }

    @Override
    public List<AttendanceInfo.Response> getBeforeSemester() {
        return attendanceRepository.findBeforeSemester().stream().map(AttendanceInfo.Response::fromEntity).toList();
    }

}
