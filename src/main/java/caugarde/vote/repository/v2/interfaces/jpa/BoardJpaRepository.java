package caugarde.vote.repository.v2.interfaces.jpa;

import caugarde.vote.model.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardJpaRepository extends JpaRepository<Board,Integer> {
}
