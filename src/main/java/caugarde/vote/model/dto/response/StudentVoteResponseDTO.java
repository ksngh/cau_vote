package caugarde.vote.model.dto.response;

import caugarde.vote.model.entity.Student;
import caugarde.vote.model.entity.Vote;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class StudentVoteResponseDTO {

    private String majority;
    private String name;

    public StudentVoteResponseDTO(Student student) {
        this.majority = student.getMajority();
        this.name = student.getName();
    }
}
