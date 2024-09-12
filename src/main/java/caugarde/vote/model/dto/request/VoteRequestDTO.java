package caugarde.vote.model.dto.request;

import lombok.Getter;

import java.sql.Timestamp;

@Getter
public class VoteRequestDTO {

    private String title;
    private String content;
    private int limitPeople;
    private Timestamp startDate;
    private Timestamp submitDate;

}
