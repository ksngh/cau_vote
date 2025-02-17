package caugarde.vote.model.dto.vote;

import lombok.Getter;

public class VoteResult {

    @Getter
    public static class SuccessMessage{
        private String message;
        public SuccessMessage(String message){
            this.message = message;
        }
        public static SuccessMessage vote() {
            return new SuccessMessage("투표가 완료되었습니다.");
        }

        public static SuccessMessage cancel() {
            return new SuccessMessage("투표가 취소되었습니다.");
        }
    }

    @Getter
    public static class Count{
        private final int count;
        private final Long boardId;
        private final int limitPeople;
        private Count(Long boardId, int count, int limitPeople){
            this.boardId = boardId;
            this.count = count;
            this.limitPeople = limitPeople;
        }
        public static Count of(Long boardId, int count, int limitPeople){
            return new Count(boardId,count,limitPeople);
        }
    }

}
