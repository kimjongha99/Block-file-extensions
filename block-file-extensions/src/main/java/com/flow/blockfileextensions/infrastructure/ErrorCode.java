package com.flow.blockfileextensions.infrastructure;


import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter

public enum ErrorCode {
    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "잘못된 매개 변수"),
    RESOURCE_NOT_FOUND(HttpStatus.BAD_REQUEST, "리소스를 찾을 수 없습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "인터넷 서버 오류"),
    DUPLICATE_EXTENSION(HttpStatus.BAD_REQUEST, "확장명 중복"),
    InValidException(HttpStatus.BAD_REQUEST, "유효성 검사 실패" ),
    CUSTOM_EXTENSION_LIMIT_EXCEEDED(HttpStatus.BAD_REQUEST, "커스텀 확장자 개수 한도를 초과");

    private final HttpStatus httpStatus;
    private final String message;
    ErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }}
