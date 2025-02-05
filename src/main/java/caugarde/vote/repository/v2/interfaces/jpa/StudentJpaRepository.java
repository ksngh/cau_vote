package caugarde.vote.repository.v2.interfaces.jpa;

import caugarde.vote.model.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentJpaRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByEmail(String email);

}
