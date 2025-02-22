package caugarde.vote.service.v2.impls;

import caugarde.vote.common.util.SemesterUtil;
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
        List<Student> students = studentService.getAllStudents();
        Map<Long, Integer> voteCounts = attendanceRepository.getStudentVoteCounts(students);

// 기존 출석 데이터 가져오기 & Map 변환
        Map<Long, Attendance> attendanceMap = students.stream()
                .map(student -> attendanceRepository.findByStudentAndSemester(
                                student, SemesterUtil.getSemester(LocalDate.now()))
                        .orElse(Attendance.of(student, 0)))
                .collect(Collectors.toMap(attendance -> attendance.getStudent().getId(), attendance -> attendance));

// 출석 횟수 업데이트
        attendanceMap.forEach((studentId, attendance) ->
                attendance.updateCount(voteCounts.getOrDefault(studentId, 0)));

// 최종 리스트 생성
        List<Attendance> updatedAttendances = new ArrayList<>(attendanceMap.values());
        attendanceRepository.saveAll(updatedAttendances);

        List<Attendance> top10Attendances = getTop10s(updatedAttendances);
        studentAttendanceCountService.deleteAllCache();
        studentAttendanceCountService.saveStudentAttendanceCount(top10Attendances.stream().map(StudentAttendanceCount::from).toList());
    }

    public List<Attendance> getTop10s(List<Attendance> attendanceList) {
        // 출석 횟수 내림차순 정렬
        List<Attendance> sortedList = attendanceList.stream()
                .sorted(Comparator.comparingInt(Attendance::getAttendanceCount).reversed()) // 내림차순 정렬
                .toList();

        // 상위 10등까지 포함된 리스트 반환
        return sortedList.stream()
                .limit(getTop10CutoffIndex(sortedList)) // 10등까지 포함한 개수만큼 자름
                .toList();
    }

    private int getTop10CutoffIndex(List<Attendance> sortedList) {
        if (sortedList.size() <= 10) {
            return sortedList.size();
        }

        int cutoffIndex = 10;
        int cutoffValue = sortedList.get(9).getAttendanceCount(); // 10번째 값

        while (cutoffIndex < sortedList.size() && sortedList.get(cutoffIndex).getAttendanceCount() == cutoffValue) {
            cutoffIndex++;
        }

        return cutoffIndex;
    }

    @Override
    public List<Attendance> getTop10Attendances(String semester) {
        return attendanceRepository.findTop10Attendance(semester);
    }

}
