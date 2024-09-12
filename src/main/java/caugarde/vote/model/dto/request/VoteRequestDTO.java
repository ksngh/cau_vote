package caugarde.vote.model.dto.request;

import lombok.Getter;

import java.sql.Timestamp;
import java.util.UUID;

@Getter
public class VoteRequestDTO {

    private final String title;
    private final String content;
    private final int limitPeople;
    private final Timestamp startDate;
    private final Timestamp submitDate;

    public VoteRequestDTO(String title, String content, int limitPeople, Timestamp startDate, Timestamp submitDate) {
        this.title = title;
        this.content = content;
        this.limitPeople = limitPeople;
        this.startDate = startDate;
        this.submitDate = submitDate;
    }
}
