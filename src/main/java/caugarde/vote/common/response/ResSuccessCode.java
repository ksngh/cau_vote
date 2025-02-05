package caugarde.vote.common.response;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResSuccessCode implements ResCode {
    SUCCESS(HttpStatus.OK.value(),  20000,"Success"),
    READ(HttpStatus.OK.value(), 20001, "Read Successfully"),
    UPDATED(HttpStatus.OK.value(), 20002, "Updated Successfully"),
    DELETED(HttpStatus.OK.value(), 20003, "Deleted Successfully"),
    SAVED(HttpStatus.OK.value(), 20004, "Data saved successfully"),
    CREATED(HttpStatus.OK.value(), 20100, "Created successfully"),
    NO_CONTENT(HttpStatus.NO_CONTENT.value(), 20400, "No content");

    private final Integer httpStatusCode;
    private final Integer code;
    private final String message;
}