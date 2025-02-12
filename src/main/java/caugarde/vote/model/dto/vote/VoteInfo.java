package caugarde.vote.model.dto.vote;

import caugarde.vote.model.entity.Vote;
import caugarde.vote.model.enums.FencingType;

public class VoteInfo {

    public static class Response{
        private String name;
        private String majority;
        private FencingType fencingType;
        private String studentId;

        private Response(Vote vote){
            this.name = vote.getStudent().getName();
            this.majority = vote.getStudent().getMajority();
            this.fencingType = vote.getFencingType();
            this.studentId = vote.getStudent().getUniversityId();
        }

        public static Response from(Vote vote){
            return new Response(vote);
        }
    }
}
