package caugarde.vote.model.dto.attendance;

import caugarde.vote.common.util.SemesterUtil;
import caugarde.vote.model.entity.Attendance;
import caugarde.vote.model.entity.cached.StudentAttendanceCount;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

public class AttendanceInfo {

    @NoArgsConstructor
    @Getter
    public static class Response{

        private String name;
        private String majority;
        private Integer count;
        private String semester;

        private Response(StudentAttendanceCount studentAttendanceCount){
            this.name = studentAttendanceCount.getName();
            this.majority = studentAttendanceCount.getMajority();
            this.count = studentAttendanceCount.getCount();
            this.semester = SemesterUtil.getSemester(LocalDate.now());
        }

        public static Response from(StudentAttendanceCount studentAttendanceCount){
            return new Response(studentAttendanceCount);
        }

        private Response(Attendance attendance){
            this.name = attendance.getStudent().getName();
            this.majority = attendance.getStudent().getMajority();
            this.count = attendance.getAttendanceCount();
            this.semester = attendance.getSemester();
        }

        public static Response fromEntity(Attendance attendance){
            return new Response(attendance);
        }

    }

}
