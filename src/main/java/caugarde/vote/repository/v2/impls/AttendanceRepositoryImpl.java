package caugarde.vote.repository.v2.impls;

import caugarde.vote.model.entity.Attendance;
import caugarde.vote.model.entity.QAttendance;
import caugarde.vote.model.entity.QVote;
import caugarde.vote.model.entity.Student;
import caugarde.vote.repository.v2.interfaces.AttendanceRepository;
import caugarde.vote.repository.v2.interfaces.jpa.AttendanceJpaRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class AttendanceRepositoryImpl implements AttendanceRepository {

    private static final QVote qVote = QVote.vote;
    private static final QAttendance qAttendance = QAttendance.attendance;
    private final JPAQueryFactory queryFactory;
    private final AttendanceJpaRepository attendanceJpaRepository;

    @Override
    public void save(Attendance attendance) {
        attendanceJpaRepository.save(attendance);
    }

    @Override
    public Map<Long, Integer> getStudentVoteCounts(List<Student> students) {
        List<Long> studentIds = students.stream().map(Student::getId).toList();

        return queryFactory
                .select(qVote.student.id, qVote.count())
                .from(qVote)
                .where(
                        qVote.student.id.in(studentIds),
                        qVote.deletedAt.isNull(),
                        qVote.createdAt.after(LocalDateTime.now().minusMonths(8)) // 8개월 이내 데이터만 조회
                )
                .groupBy(qVote.student.id)
                .fetch()
                .stream()
                .collect(Collectors.toMap(
                        tuple -> tuple.get(qVote.student.id),
                        tuple -> Optional.ofNullable(tuple.get(qVote.count())).orElse(0L).intValue()
                ));
    }

    @Override
    public void saveAll(List<Attendance> attendances) {
        attendanceJpaRepository.saveAll(attendances);
    }

    @Override
    public List<Attendance> findTop10Attendance(String semester) {
        return queryFactory
                .selectFrom(qAttendance)
                .where(qAttendance.semester.eq(semester))
                .orderBy(qAttendance.attendanceCount.desc())
                .limit(10)
                .fetch();

    }

    @Override
    public Optional<Attendance> findByStudentAndSemester(Student student, String semester) {
        return attendanceJpaRepository.findByStudentAndSemester(student, semester);
    }

}
