package com.gbsw.gbswhub.domain.global.Error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다."),

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),

    CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "카테고리를 찾을 수 없습니다"),

    PROJECT_NOT_FOUND(HttpStatus.NOT_FOUND, "프로젝트 모집을 찾을 수 없습니다"),

    MENTORING_NOT_FOUND(HttpStatus.NOT_FOUND, "멘토멘티 모집을 찾을 수 없습니다"),

    CLUB_NOT_FOUND(HttpStatus.NOT_FOUND, "동아리 모집 공고를 찾을 수 없습니다"),

    PART_MENTORING_NOT_FOUND(HttpStatus.NOT_FOUND, "멘토멘티 신청을 찾을 수 없습니다."),

    PART_CLUB_NOT_FOUND(HttpStatus.NOT_FOUND, "동아리 신청을 찾을 수 없습니다."),

    PART_NOT_FOUND(HttpStatus.NOT_FOUND, "신청을 찾을 수 없습니다."),

    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다."),

    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "토큰 기한이 만료되었습니다."),

    ACCESS_DENIED(HttpStatus.FORBIDDEN, "접근 권한이 없습니다."),

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류가 발생했습니다."),

    USERNAME_DUPLICATION(HttpStatus.CONFLICT, "이미 사용 중인 아이디입니다."),

    ALREADY_PARTICIPATED(HttpStatus.CONFLICT, "이미 신청한 모집글입니다.");

    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

}
