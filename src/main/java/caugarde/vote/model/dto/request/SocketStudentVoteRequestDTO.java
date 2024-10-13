package caugarde.vote.model.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class SocketStudentVoteRequestDTO {

    private UUID voteId;
    private String studentEmail;

    public SocketStudentVoteRequestDTO(UUID voteId) {
        this.voteId = voteId;
    }
    public void setEmailBySession(String studentEmail){
        this.studentEmail = studentEmail;
    }
}
