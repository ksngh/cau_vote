package caugarde.vote.repository.v2.impls;

import caugarde.vote.model.dto.student.StudentInfo;
import caugarde.vote.model.entity.QStudent;
import caugarde.vote.model.entity.Student;
import caugarde.vote.repository.v2.interfaces.StudentRepository;
import caugarde.vote.repository.v2.interfaces.jpa.StudentJpaRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class StudentRepositoryImpl implements StudentRepository {

    private static final QStudent qStudent = QStudent.student;

    private final JPAQueryFactory queryFactory;
    private final StudentJpaRepository studentJpaRepository;

    @Override
    public void save(Student student) {
        studentJpaRepository.save(student);
    }

    @Override
    public List<Student> findAll() {
        return studentJpaRepository.findAll();
    }

    @Override
    public Optional<Student> findByEmail(String email) {
        return studentJpaRepository.findByEmail(email);
    }

    @Override
    public Optional<Student> findById(Long id) {
        return studentJpaRepository.findById(id);
    }

    @Override
    public Slice<StudentInfo.Response> pageStudents(Long cursorId, int size) {
        List<StudentInfo.Response> students = queryFactory
                .select(Projections.constructor(
                        StudentInfo.Response.class,
                        qStudent.id,
                        qStudent.name,
                        qStudent.majority,
                        qStudent.universityId,
                        qStudent.overdueFine,
                        qStudent.authorities
                ))
                .from(qStudent)
                .where((cursorId == null) ? null : qStudent.id.gt(cursorId), qStudent.deletedAt.isNull())
                .orderBy(qStudent.id.asc())
                .limit(size + 1)
                .fetch();

        boolean hasNext = students.size() > size;
        if (hasNext) {
            students.remove(students.size() - 1);
        }

        return new SliceImpl<>(students, PageRequest.of(0, size), hasNext);
    }


}
