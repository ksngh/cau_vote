package caugarde.vote.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SuccessMessage {

    DELETE(" 삭제가 완료되었습니다."),
    CREATE(" 생성이 완료되었습니다."),
    UPDATE(" 수정이 완료되었습니다.");

    private final String message;

}