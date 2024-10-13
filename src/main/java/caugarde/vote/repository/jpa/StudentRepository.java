package caugarde.vote.repository.jpa;

import caugarde.vote.model.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface StudentRepository extends JpaRepository<Student, UUID> {

    Optional<Student> findByEmail(String email);

    @Query("SELECT s.studentPk, COUNT(v) " +
            "FROM Student s " +
            "LEFT JOIN StudentVote v ON v.student.studentPk = s.studentPk " +
            "GROUP BY s.studentPk " +
            "ORDER BY COUNT(v) DESC")
    List<Object[]> findVoteCountByStudent();



}
