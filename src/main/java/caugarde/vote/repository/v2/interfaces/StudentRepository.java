package caugarde.vote.repository.v2.interfaces;

import caugarde.vote.model.entity.Student;

import java.util.Optional;

public interface StudentRepository {

    void save (Student student);

    Optional<Student> findByEmail(String email);

}
