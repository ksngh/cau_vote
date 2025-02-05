package caugarde.vote.model.dto.board;

import java.time.LocalDateTime;

public class BoardInfo {


    public static class Response {
        private Integer id;
        private String title;
        private String content;
        private String status;
        private Integer limitPeople;
        private LocalDateTime startDate;
        private LocalDateTime endDate;
    }

}
