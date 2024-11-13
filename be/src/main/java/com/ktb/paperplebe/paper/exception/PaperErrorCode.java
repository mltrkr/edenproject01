package com.ktb.paperplebe.paper.exception;

import com.ktb.paperplebe.common.exception.DomainErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum PaperErrorCode implements DomainErrorCode {
    PAPER_NOT_FOUND("요청하신 페이퍼를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    PAPER_CREATION_FAILED("페이지 생성에 실패하였습니다.", HttpStatus.INTERNAL_SERVER_ERROR);

    private final String message;
    private final HttpStatus status;

    PaperErrorCode(final String message, final HttpStatus status) {
        this.message = message;
        this.status = status;
    }
}
