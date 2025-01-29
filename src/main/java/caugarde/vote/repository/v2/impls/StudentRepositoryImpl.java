package caugarde.vote.repository.v2.impls;

import caugarde.vote.repository.v2.interfaces.StudentRepository;
import caugarde.vote.repository.v2.interfaces.jpa.StudentJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class StudentRepositoryImpl implements StudentRepository {

    private final StudentJpaRepository studentJpaRepository;

}
