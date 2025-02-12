package caugarde.vote.repository.v2.interfaces.cached;

import caugarde.vote.model.entity.cached.StudentAttendanceCount;

import java.util.List;

public interface StudentAttendanceCountRepository {

    List<StudentAttendanceCount> findTop10s();

    List<StudentAttendanceCount> findTop1s();

    void saveStudentAttendanceCounts(List<StudentAttendanceCount> studentAttendanceCount);

    void clearAllCache();

}
