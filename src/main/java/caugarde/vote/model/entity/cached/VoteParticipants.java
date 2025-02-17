package caugarde.vote.model.entity.cached;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VoteParticipants implements Serializable {

    @Id
    private Long boardId; // Redis Key

    private Integer participantsCount;

    private VoteParticipants(Long boardId) {
        this.boardId = boardId;
        this.participantsCount = 0;
    }

    public static VoteParticipants create(Long boardId) {
        return new VoteParticipants(boardId);
    }

    private VoteParticipants(Long boardId,Integer participantsCount) {
        this.boardId = boardId;
        this.participantsCount = participantsCount;
    }

    public static VoteParticipants of(Long boardId, Integer participantsCount) {
        return new VoteParticipants(boardId,participantsCount);
    }


}
