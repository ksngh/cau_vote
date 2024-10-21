package caugarde.vote.model.dto.response;

import caugarde.vote.model.entity.Student;
import caugarde.vote.model.entity.StudentVote;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class StudentVoteResponseDTO {

    private String studentId;
    private String majority;
    private String name;
    private String category;

    public StudentVoteResponseDTO(StudentVote studentVote) {
        this.studentId = studentVote.getStudent().getStudentId();
        this.majority = studentVote.getStudent().getMajority();
        this.name = studentVote.getStudent().getName();
        this.category = studentVote.getCategory().getType().getName();
    }

        public record CreateMessage(String message) {}

        public record CreateAttendanceNumber(UUID votePk, int number, int limitPeople) {}

        public record DeleteMessage(String message) {}
}
