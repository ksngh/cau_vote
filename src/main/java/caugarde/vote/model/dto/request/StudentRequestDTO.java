package caugarde.vote.model.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StudentRequestDTO {
    String name;
    String majority;
    String memberType;
    String studentId;

    public StudentRequestDTO(String name, String majority, String memberType, String studentId) {
        this.name = name;
        this.majority = majority;
        this.memberType = memberType;
        this.studentId = studentId;
    }
}
