package caugarde.vote.model.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class StudentVoteRequestDTO {

    private int choice;

    public StudentVoteRequestDTO(int choice) {
        this.choice = choice;
    }
}
