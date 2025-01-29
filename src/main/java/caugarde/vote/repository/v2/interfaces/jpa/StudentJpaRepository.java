package caugarde.vote.repository.v2.interfaces.jpa;

import caugarde.vote.model.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentJpaRepository extends JpaRepository<Integer, Student> {
}
