package caugarde.vote.service.v2.interfaces;

import caugarde.vote.model.dto.attendance.AttendanceInfo;
import caugarde.vote.model.entity.Attendance;

import java.util.List;

public interface AttendanceService {

    void createAttendance();

    List<Attendance> getTop10Attendances(String semester);

}
