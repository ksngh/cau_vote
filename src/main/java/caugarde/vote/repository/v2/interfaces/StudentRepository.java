package caugarde.vote.repository.v2.interfaces;

import caugarde.vote.model.entity.Student;

import java.util.List;
import java.util.Optional;

public interface StudentRepository {

    void save (Student student);

    List<Student> findAll();

    Optional<Student> findByEmail(String email);

    Optional<Student> findById(Long id);

}
