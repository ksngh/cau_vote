package caugarde.vote.model.dto.board;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class BoardInfo {

    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Getter
    @AllArgsConstructor
    public static class Response {
        private Long id;
        private String title;
        private String content;
        private String status;
        private Integer limitPeople;
        private LocalDateTime startDate;
        private LocalDateTime endDate;
    }

}
