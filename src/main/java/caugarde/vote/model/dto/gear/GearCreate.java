package caugarde.vote.model.dto.gear;

import caugarde.vote.model.enums.FencingType;
import caugarde.vote.model.enums.GearType;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class GearCreate {

    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Getter
    public static class Request {

        @NotNull(message = "장비 번호는 필수 입력값입니다.")
        private Integer num;

        @NotNull(message = "종목을 선택해 주세요.")
        private FencingType fencingType;

        @NotNull(message = "장비 종류를 선택해 주세요.")
        private GearType gearType;

    }
}
