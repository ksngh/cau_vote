package caugarde.vote.model.dto.board;

import caugarde.vote.model.entity.Board;
import caugarde.vote.model.enums.BoardStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

public class BoardInfo {

    @Getter
    @AllArgsConstructor
    public static class Response {
        private Long id;
        private String title;
        private String content;
        private BoardStatus status;
        private Integer limitPeople;
        private LocalDateTime startDate;
        private LocalDateTime endDate;

        private Response(Board board) {
            this.id = board.getId();
            this.title = board.getTitle();
            this.content = board.getContent();
            this.status = board.getStatus();
            this.limitPeople = board.getLimitPeople();
            this.startDate = board.getStartDate();
            this.endDate = board.getEndDate();
        }

        public static Response from(Board board) {
            return new Response(board);
        }
    }

}
