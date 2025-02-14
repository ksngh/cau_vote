package caugarde.vote.model.dto.vote;

import caugarde.vote.model.enums.FencingType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class VoteCreate {


    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Getter
    public static class Request{
        @NotBlank(message = "종목을 선택해주시기 바랍니다.")
        private FencingType fencingType;
    }

}
