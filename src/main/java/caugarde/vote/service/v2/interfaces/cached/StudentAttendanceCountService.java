package caugarde.vote.service.v2.interfaces.cached;

import caugarde.vote.model.entity.cached.StudentAttendanceCount;

import java.util.List;

public interface StudentAttendanceCountService {

    void saveStudentAttendanceCount(List<StudentAttendanceCount> studentAttendanceCounts);

    List<StudentAttendanceCount> getTop1();

    List<StudentAttendanceCount> getTop10();

}
