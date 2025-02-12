package caugarde.vote.model.entity.cached;

import caugarde.vote.model.entity.Attendance;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Getter
@AllArgsConstructor(access= AccessLevel.PROTECTED)
@RedisHash("studentAttendance:")
public class StudentAttendanceCount implements Serializable {

    @Id
    private Long studentId;

    private String majority;
    private String name;
    private Integer count;

    private StudentAttendanceCount(Attendance attendance) {
        this.studentId = attendance.getStudent().getId();
        this.majority = attendance.getStudent().getMajority();
        this.name = attendance.getStudent().getName();
        this.count=attendance.getAttendanceCount();
    }

    public static StudentAttendanceCount from(Attendance attendance) {
        return new StudentAttendanceCount(attendance);
    }

}
