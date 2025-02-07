package caugarde.vote.model.entity.cached;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class VoteParticipants {

    private int participantsCount;

    public void increment() {
       this.participantsCount += 1;
    }

    public void decrement() {

        this.participantsCount -= 1;
    }

}
