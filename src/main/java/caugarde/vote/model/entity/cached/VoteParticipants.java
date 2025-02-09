package caugarde.vote.model.entity.cached;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@AllArgsConstructor
@Getter
@RedisHash(value = "voteParticipants")
public class VoteParticipants implements Serializable {

    @Id
    private Long boardId; // Redis Key

    private Integer participantsCount;

    public void increment() {
        this.participantsCount++;
    }

    public void decrement() {
        if (this.participantsCount > 0) {
            this.participantsCount--;
        }
    }

}
