package caugarde.vote.model.dto.attendance;

import caugarde.vote.model.entity.cached.StudentAttendanceCount;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class AttendanceInfo {

    @NoArgsConstructor
    @Getter
    public static class Response{

        private String name;
        private String majority;
        private Integer count;

        private Response(StudentAttendanceCount studentAttendanceCount){
            this.name = studentAttendanceCount.getName();
            this.majority = studentAttendanceCount.getMajority();
            this.count = studentAttendanceCount.getCount();
        }

        public static Response from(StudentAttendanceCount studentAttendanceCount){
            return new Response(studentAttendanceCount);
        }

    }

}
