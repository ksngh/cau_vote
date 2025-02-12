package caugarde.vote.model.entity;

import caugarde.vote.common.util.SemesterUtil;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "Attendance")
@NoArgsConstructor (access = AccessLevel.PROTECTED)
@Getter
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @JoinColumn(name = "STUDENT", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Student student;

    @Column(name = "SEMESTER", nullable = false, length = 30)
    private String semester;

    @Column(name = "ATTENDANCE_COUNT", nullable = false)
    private Integer attendanceCount;

    private Attendance (Student student, Integer attendanceCount) {
        this.student = student;
        this.semester = SemesterUtil.getSemester(LocalDate.now());
        this.attendanceCount = attendanceCount;
    }

    public static Attendance of(Student student, Integer attendanceCount) {
        return new Attendance(student, attendanceCount);
    }

}
