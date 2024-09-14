package caugarde.vote.model.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class VoteRequestDTO {

    private String title;
    private String content;
    private int limitPeople;
    private Timestamp startDate;
    private Timestamp submitDate;

    public VoteRequestDTO(String title, String content, int limitPeople, Timestamp startDate, Timestamp submitDate) {
        this.title = title;
        this.content = content;
        this.limitPeople = limitPeople;
        this.startDate = startDate;
        this.submitDate = submitDate;
    }
}
