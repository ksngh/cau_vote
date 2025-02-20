package caugarde.vote.model.dto.student;

import caugarde.vote.model.enums.MemberType;
import caugarde.vote.model.enums.Role;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class StudentDetailsUpdate {

    @NoArgsConstructor(access= AccessLevel.PROTECTED)
    @Getter
    public static class Request{
        private String universityId;
        private String name;
        private String majority;
        private MemberType memberType;
        private Role role;
    }
}
