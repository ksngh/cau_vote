package caugarde.vote.service.v2.interfaces;

import caugarde.vote.model.dto.attendance.AttendanceInfo;

import java.util.List;

public interface AttendanceService {

    List<AttendanceInfo.Response> getTopParticipants();

    List<AttendanceInfo.Response> getTopTenParticipants();

    void createAttendance();
}
