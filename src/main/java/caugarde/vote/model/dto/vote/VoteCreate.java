package caugarde.vote.model.dto.vote;

import caugarde.vote.model.enums.FencingType;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class VoteCreate {


    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Request {
        @NotNull(message = "종목을 선택해주시기 바랍니다.")
        private FencingType fencingType;
    }

}
