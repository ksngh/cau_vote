package caugarde.vote.model.dto.vote;

import caugarde.vote.model.entity.Vote;
import caugarde.vote.model.enums.FencingType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class VoteInfo {

    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Getter
    public static class Response{
        private String name;
        private String majority;
        private FencingType fencingType;
        private String universityId;

        private Response(Vote vote){
            this.name = vote.getStudent().getName();
            this.majority = vote.getStudent().getMajority();
            this.fencingType = vote.getFencingType();
            this.universityId = vote.getStudent().getUniversityId();
        }

        public static Response from(Vote vote){
            return new Response(vote);
        }
    }
}
