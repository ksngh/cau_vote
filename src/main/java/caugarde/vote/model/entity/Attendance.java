package caugarde.vote.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Attendance")
@NoArgsConstructor (access = AccessLevel.PROTECTED)
@Getter
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer id;

    @JoinColumn(name = "STUDENT", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Student student;

    @Column(name = "SEMESTER", nullable = false, length = 30)
    private String semester;

    @Column(name = "ATTENDANCE_COUNT", nullable = false)
    private Integer attendanceCount;

    private Attendance (Student student, String semester, Integer attendanceCount) {
        this.student = student;
        this.semester = semester;
        this.attendanceCount = attendanceCount;
    }

    public static Attendance of (Student student, String semester, Integer attendanceCount) {
        return new Attendance(student, semester, attendanceCount);
    }
}
