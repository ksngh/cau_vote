package caugarde.vote.model.dto.vote;

import lombok.Getter;

public class VoteResult {


    public static class SuccessMessage{
        private String message;
        private SuccessMessage(String message){
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
        private Count(int count){
            this.count = count;
        }
        public static Count of(int count){
            return new Count(count);
        }
    }

}
