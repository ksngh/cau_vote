package caugarde.vote.common.response;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL) // Null 필드 제외
@NoArgsConstructor
public class CustomApiResponse<T> {

    private Integer httpStatusCode;
    private Integer code;
    private String message;
    private String description;
    private List<String> errorList;
    private T data;

    private CustomApiResponse(ResCode resCode, String description, List<String> errorList, T data) {
        this.httpStatusCode = resCode.getHttpStatusCode();
        this.code = resCode.getCode();
        this.message = resCode.getMessage();
        this.description = description;
        this.errorList = errorList;
        this.data = data;
    }

    // 성공 응답 생성
    public static CustomApiResponse<Void> OK(ResCode resCode) {
        return new CustomApiResponse<>(resCode, null, null, null);
    }

    public static <T> CustomApiResponse<T> OK(ResCode resCode, T data) {
        return new CustomApiResponse<>(resCode, null, null, data);
    }


    // 에러 응답 생성
    public static CustomApiResponse<Void> ERROR(ResCode resCode, String description) {
        return new CustomApiResponse<>(resCode, description, null, null);
    }

    public static CustomApiResponse<Void> ERROR(ResCode resCode, String description, List<String> errorList) {
        return new CustomApiResponse<>(resCode, description, errorList, null);
    }
}
