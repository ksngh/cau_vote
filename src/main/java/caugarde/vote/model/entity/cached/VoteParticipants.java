package caugarde.vote.model.entity.cached;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Getter
@RedisHash(value = "voteParticipants")
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

}
