package caugarde.vote.repository.v2.impls;

import caugarde.vote.model.entity.Board;
import caugarde.vote.repository.v2.interfaces.BoardRepository;
import caugarde.vote.repository.v2.interfaces.jpa.BoardJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardRepository {

    private final BoardJpaRepository boardJpaRepository;

    @Override
    public void save(Board board) {
        boardJpaRepository.save(board);
    }

}
