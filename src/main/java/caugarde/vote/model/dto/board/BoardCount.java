package caugarde.vote.model.dto.board;

import lombok.Getter;

public class BoardCount {

    @Getter
    public static class Response {
        private Integer count;

        public Response(Integer count) {
            this.count = count;
        }

        public static Response of(Integer count) {
            return new Response(count);
        }
    }
}
