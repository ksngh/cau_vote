package caugarde.vote.repository.v2.interfaces.jpa;

import caugarde.vote.model.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoardJpaRepository extends JpaRepository<Board,Long> {

    Optional<Board> findByDeletedAtIsNullAndId(Long id);
}
