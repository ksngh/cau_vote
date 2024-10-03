package caugarde.vote.model.dto.response;

import lombok.Getter;

@Getter
public class StudentVoteCountResponseDTO {

    private int countStudentVote;

    public StudentVoteCountResponseDTO(int countStudentVote) {
        this.countStudentVote = countStudentVote;
    }
}
