package caugarde.vote.model.dto.board;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class BoardUpdate {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Request {
        @NotBlank(message = "제목은 필수 입력 값입니다.")
        private String title;

        @NotBlank(message = "내용은 필수 입력 값입니다.")
        private String content;

        @NotNull(message = "인원 제한은 필수 입력 값입니다.")
        @Min(value = 1, message = "참여 가능 인원은 1명 이상이어야 합니다.")
        private Integer limitPeople;

        @NotNull(message = "시작 날짜는 필수 입력 값입니다.")
        private LocalDateTime startDate;

        @NotNull(message = "종료 날짜는 필수 입력 값입니다.")
        private LocalDateTime endDate;
    }

}
