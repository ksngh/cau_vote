package caugarde.vote.model.dto.student;

import caugarde.vote.model.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

public class StudentInfo {

    @NoArgsConstructor
    @Getter
    @AllArgsConstructor
    public static class Response {
        private Long id;
        private String name;
        private String majority;
        private String universityId;
        private Integer overdueFine;
        private Set<Role> role;
    }

}
