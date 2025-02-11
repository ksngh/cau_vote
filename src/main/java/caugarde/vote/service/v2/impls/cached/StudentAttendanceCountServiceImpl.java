package caugarde.vote.service.v2.impls.cached;

import caugarde.vote.model.entity.Attendance;
import caugarde.vote.model.entity.cached.StudentAttendanceCount;
import caugarde.vote.repository.v2.interfaces.cached.StudentAttendanceCountRepository;
import caugarde.vote.service.v2.interfaces.AttendanceService;
import caugarde.vote.service.v2.interfaces.cached.StudentAttendanceCountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentAttendanceCountServiceImpl implements StudentAttendanceCountService {

    private final StudentAttendanceCountRepository studentAttendanceCountRepository;

    @Override
    public void saveStudentAttendanceCount(List<StudentAttendanceCount> studentAttendanceCounts) {
        studentAttendanceCountRepository.saveStudentAttendanceCounts(studentAttendanceCounts);
    }

    @Override
    public List<StudentAttendanceCount> getTop1() {
        return studentAttendanceCountRepository.findTop1s();
    }

    @Override
    public List<StudentAttendanceCount> getTop10() {
        return studentAttendanceCountRepository.findTop10s();
    }


}
