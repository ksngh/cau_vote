package caugarde.vote.model.dto.response;

import caugarde.vote.model.entity.Vote;
import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;
import java.util.UUID;

@Getter
public class VoteResponseDTO {

    private final UUID uuid;
    private final String title;
    private final String content;
    private final Timestamp startDate;
    private final Timestamp submitDate;
    private final int limitPeople;

    @Builder
    public VoteResponseDTO(UUID uuid, String title, String content, Timestamp startDate, Timestamp submitDate, int limitPeople) {
        this.uuid = uuid;
        this.title = title;
        this.content = content;
        this.startDate = startDate;
        this.submitDate = submitDate;
        this.limitPeople = limitPeople;
    }

    public VoteResponseDTO(Vote vote){
        this.uuid = vote.getVotePk();
        this.title = vote.getTitle();
        this.content = vote.getContent();
        this.startDate = vote.getStartDate();
        this.submitDate = vote.getSubmitDate();
        this.limitPeople = vote.getLimitPeople();
    }
}
