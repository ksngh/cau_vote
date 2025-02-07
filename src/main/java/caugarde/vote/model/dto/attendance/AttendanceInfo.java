package caugarde.vote.model.dto.attendance;

import caugarde.vote.model.entity.cached.MostActiveParticipant;

public class AttendanceInfo {

    public static class Response{

        private String name;
        private String majority;
        private Integer count;

        private Response(MostActiveParticipant mostActiveParticipant){
            this.name = mostActiveParticipant.getName();
            this.majority = mostActiveParticipant.getMajority();
            this.count = mostActiveParticipant.getCount();
        }

        public static Response from(MostActiveParticipant mostActiveParticipant){
            return new Response(mostActiveParticipant);
        }

    }

}
