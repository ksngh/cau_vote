package caugarde.vote.model.entity.cached;

import caugarde.vote.model.entity.Attendance;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
public class StudentAttendanceCount implements Serializable {

    private Long studentId;
    private String majority;
    private String name;
    private Integer count;

    private StudentAttendanceCount(Attendance attendance) {
        this.studentId = attendance.getStudent().getId();
        this.majority = attendance.getStudent().getMajority();
        this.name = attendance.getStudent().getName();
        this.count = attendance.getAttendanceCount();
    }

    public static StudentAttendanceCount from(Attendance attendance) {
        return new StudentAttendanceCount(attendance);
    }

}
