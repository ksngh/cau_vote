package caugarde.vote.model.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
public class VoteResponseDTO {

    private final String title;
    private final String content;
    private final Timestamp startDate;
    private final Timestamp submitDate;
    private final int limitPeople;

    @Builder
    public VoteResponseDTO(String title, String content, Timestamp startDate, Timestamp submitDate, int limitPeople) {
        this.title = title;
        this.content = content;
        this.startDate = startDate;
        this.submitDate = submitDate;
        this.limitPeople = limitPeople;
    }
}
