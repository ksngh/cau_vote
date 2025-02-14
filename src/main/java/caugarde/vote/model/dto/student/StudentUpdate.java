package caugarde.vote.model.dto.student;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class StudentUpdate {

    @NoArgsConstructor(access= AccessLevel.PROTECTED)
    @Getter
    public static class Request{
        private String universityId;
        private String name;
        private String majority;
        private String memberType;
    }

}
